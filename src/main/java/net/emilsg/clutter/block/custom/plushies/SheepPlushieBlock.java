package net.emilsg.clutter.block.custom.plushies;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class SheepPlushieBlock extends AbstractPlushieBlock {
    public static final EnumProperty<SheepColors> COLOR = EnumProperty.of("color", SheepColors.class);

    public SheepPlushieBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(COLOR, SheepColors.WHITE));
    }

    public static final MapCodec<SheepPlushieBlock> CODEC = createCodec(SheepPlushieBlock::new);

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, COLOR);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        double random = Math.random();
        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isOf(Items.AIR) && !stack.isIn(ModItemTags.DYES)) {
            return ActionResult.PASS;
        }

        if (hand.equals(Hand.MAIN_HAND)) {
            if (stack.isEmpty()) {
                world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.25f);
                if (world.isClient) {
                    world.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, random, 0.0, 0.0);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.CONSUME;
            }
            if (stack.isIn(ModItemTags.DYES)) {

                Item item = stack.getItem();
                SheepColors color = state.get(COLOR);

                Map<Item, SheepColors> dyeColorMap = new HashMap<>();
                dyeColorMap.put(Items.WHITE_DYE, SheepColors.WHITE);
                dyeColorMap.put(Items.ORANGE_DYE, SheepColors.ORANGE);
                dyeColorMap.put(Items.MAGENTA_DYE, SheepColors.MAGENTA);
                dyeColorMap.put(Items.LIGHT_BLUE_DYE, SheepColors.LIGHT_BLUE);
                dyeColorMap.put(Items.YELLOW_DYE, SheepColors.YELLOW);
                dyeColorMap.put(Items.LIME_DYE, SheepColors.LIME);
                dyeColorMap.put(Items.PINK_DYE, SheepColors.PINK);
                dyeColorMap.put(Items.GRAY_DYE, SheepColors.GRAY);
                dyeColorMap.put(Items.LIGHT_GRAY_DYE, SheepColors.LIGHT_GRAY);
                dyeColorMap.put(Items.CYAN_DYE, SheepColors.CYAN);
                dyeColorMap.put(Items.PURPLE_DYE, SheepColors.PURPLE);
                dyeColorMap.put(Items.BLUE_DYE, SheepColors.BLUE);
                dyeColorMap.put(Items.BROWN_DYE, SheepColors.BROWN);
                dyeColorMap.put(Items.GREEN_DYE, SheepColors.GREEN);
                dyeColorMap.put(Items.RED_DYE, SheepColors.RED);
                dyeColorMap.put(Items.BLACK_DYE, SheepColors.BLACK);

                if (dyeColorMap.containsKey(item) && color != dyeColorMap.get(item)) {
                    color = dyeColorMap.get(item);
                    world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                    world.setBlockState(pos, state.with(COLOR, color), Block.NOTIFY_ALL);
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return ActionResult.CONSUME;
    }


    public enum SheepColors implements StringIdentifiable {
        WHITE("white"),
        LIGHT_GRAY("light_gray"),
        GRAY("gray"),
        BLACK("black"),
        BROWN("brown"),
        RED("red"),
        ORANGE("orange"),
        YELLOW("yellow"),
        LIME("lime"),
        GREEN("green"),
        CYAN("cyan"),
        LIGHT_BLUE("light_blue"),
        BLUE("blue"),
        PURPLE("purple"),
        MAGENTA("magenta"),
        PINK("pink");

        private final String name;

        SheepColors(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
