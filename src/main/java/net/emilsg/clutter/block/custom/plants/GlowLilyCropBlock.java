package net.emilsg.clutter.block.custom.plants;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GlowLilyCropBlock extends CropBlock {
    public static final IntProperty AGE = Properties.AGE_2;
    public static final BooleanProperty CLIPPED = ModProperties.CLIPPED;
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 9.0, 11.0),
            Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 12.0,12.0),
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)};

    public GlowLilyCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(AGE, 0).with(CLIPPED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if(stackInHand.getItem() instanceof ShearsItem && !state.get(CLIPPED)) {
            if(!world.isClient) world.setBlockState(pos, state.with(CLIPPED, true), Block.NOTIFY_ALL);
            world.playSound(null, pos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if(!player.getAbilities().creativeMode) stackInHand.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, CLIPPED);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state) && !state.get(CLIPPED);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) {
            super.randomTick(state, world, pos, random);
        }

    }

    @Override
    public BlockState withAge(int age) {
        return age == 3 ? ModBlocks.GLOWLILY.getDefaultState() : this.getDefaultState().with(this.getAgeProperty(), age);
    }

    protected ItemConvertible getSeedsItem() {
        return ModItems.GLOWLILY_SEEDLING;
    }

    public int getMaxAge() {
        return 3;
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        applyGrowth(world, pos, state);
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int growth = this.getAge(state) + 1;
        int maxAge = this.getMaxAge();
        if (growth > maxAge) {
            growth = maxAge;
        }

        world.setBlockState(pos, this.withAge(growth).with(CLIPPED, false), 2);
    }
}
