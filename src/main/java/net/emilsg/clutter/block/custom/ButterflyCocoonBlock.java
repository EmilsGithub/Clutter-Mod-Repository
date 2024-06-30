package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionTypes;

public class ButterflyCocoonBlock extends Block {
    public static final BooleanProperty CAN_HATCH = ModProperties.CAN_HATCH;
    public static final IntProperty HATCH = ModProperties.HATCH;
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 15, 7, 9, 16, 9),
            Block.createCuboidShape(6, 10, 6, 10, 15, 10),
            Block.createCuboidShape(7, 9, 7, 9, 10, 9)
    );

    public ButterflyCocoonBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HATCH, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(hand);
        if (world.isClient && heldItem.isOf(Items.SHEARS) && state.get(CAN_HATCH)) {
            return ActionResult.SUCCESS;
        }
        if (!world.isClient && heldItem.isOf(Items.SHEARS) && state.get(CAN_HATCH)) {
            player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                heldItem.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
            }
            world.setBlockState(pos, state.with(CAN_HATCH, false).with(HATCH, 0), Block.NOTIFY_ALL);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldHatchProgress(world, state)) {
            int i = state.get(HATCH);
            if (i < 2) {
                world.playSound(null, pos, SoundEvents.BLOCK_MOSS_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlockState(pos, state.with(HATCH, i + 1), 2);
            } else {
                world.playSound(null, pos, SoundEvents.BLOCK_MOSS_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.removeBlock(pos, false);
                if (random.nextInt(10) == 0)
                    dropStack(world, pos, new ItemStack(ModItems.BUTTERFLY_ELYTRA_SMITHING_TEMPLATE_SHARDS));

                for (int j = 0; j < 1; ++j) {
                    world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
                    ButterflyEntity butterflyEntity = ModEntities.BUTTERFLY.create(world);
                    if (butterflyEntity != null) {
                        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
                        ButterflyVariant variant = ButterflyVariant.byId(1);
                        if (registryEntry.isIn(BiomeTags.IS_OVERWORLD)) {
                            variant = ButterflyVariant.byId(random.nextInt(16));
                        } else if (registryEntry.isIn(BiomeTags.IS_NETHER)) {
                            if (registryEntry.matchesKey(BiomeKeys.WARPED_FOREST)) {
                                variant = ButterflyVariant.WARPED;
                            } else if (registryEntry.matchesKey(BiomeKeys.CRIMSON_FOREST)) {
                                variant = ButterflyVariant.CRIMSON;
                            } else if (registryEntry.matchesKey(BiomeKeys.SOUL_SAND_VALLEY)) {
                                variant = ButterflyVariant.SOUL;
                            } else if (random.nextBoolean()) {
                                variant = ButterflyVariant.CRIMSON;
                            } else if (random.nextBoolean()) {
                                variant = ButterflyVariant.WARPED;
                            } else {
                                variant = ButterflyVariant.SOUL;
                            }
                        }
                        butterflyEntity.setBreedingAge(6000);
                        butterflyEntity.refreshPositionAndAngles((double) pos.getX() + 0.3 + (double) j * 0.2, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.3, 0.0F, 0.0F);
                        butterflyEntity.setVariant(variant);
                        world.spawnEntity(butterflyEntity);
                    }
                }
            }
        }
    }

    private boolean shouldHatchProgress(World world, BlockState state) {
        boolean isDay = world.isDay();
        boolean isNether = world.getDimensionKey() == DimensionTypes.THE_NETHER;
        if ((isDay && state.get(CAN_HATCH)) || isNether) {
            return true;
        } else if (state.get(CAN_HATCH)) {
            return world.random.nextInt(500) == 0;
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HATCH, CAN_HATCH);
        super.appendProperties(builder);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(200 + random.nextInt(200)) <= 0) {
            world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.BLOCK_MOSS_FALL, SoundCategory.BLOCKS, 0.25f, 1.25f, false);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).getBlock() instanceof LeavesBlock || world.getBlockState(pos.up()).isIn(BlockTags.LOGS) || world.getBlockState(pos.up()).isIn(BlockTags.WART_BLOCKS) || world.getBlockState(pos.up()).isOf(Blocks.BONE_BLOCK);
    }

    @Override
    @Deprecated
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockState(pos.up()).isAir()) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
