package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SulphurItem extends Item {

    private static final Map<Block, Block> BLOCK_SWITCH_MAP = new HashMap<>();

    static {
        BLOCK_SWITCH_MAP.put(ModBlocks.ONYX_BLOCK, ModBlocks.BLACK_ONYX_BLOCK);
        BLOCK_SWITCH_MAP.put(ModBlocks.ONYX_SLAB, ModBlocks.BLACK_ONYX_SLAB);
        BLOCK_SWITCH_MAP.put(ModBlocks.ONYX_STAIRS, ModBlocks.BLACK_ONYX_STAIRS);
        BLOCK_SWITCH_MAP.put(ModBlocks.ONYX_WALL, ModBlocks.BLACK_ONYX_WALL);
        BLOCK_SWITCH_MAP.put(ModBlocks.POLISHED_ONYX, ModBlocks.POLISHED_BLACK_ONYX);
        BLOCK_SWITCH_MAP.put(ModBlocks.POLISHED_ONYX_SLAB, ModBlocks.POLISHED_BLACK_ONYX_SLAB);
        BLOCK_SWITCH_MAP.put(ModBlocks.POLISHED_ONYX_STAIRS, ModBlocks.POLISHED_BLACK_ONYX_STAIRS);
        BLOCK_SWITCH_MAP.put(ModBlocks.POLISHED_ONYX_WALL, ModBlocks.POLISHED_BLACK_ONYX_WALL);
    }

    public SulphurItem(Settings settings) {
        super(settings);
    }

    static Optional<Block> getBlockResult(Block block) {
        return Optional.ofNullable(BLOCK_SWITCH_MAP.get(block));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stackInHand = ctx.getStack();
        Random random = ctx.getWorld().getRandom();

        if (state.getBlock() instanceof Oxidizable) {
            Optional<BlockState> increasedOxidationState = ((Oxidizable) state.getBlock()).getDegradationResult(state);

            if (increasedOxidationState.isPresent()) {
                world.setBlockState(pos, increasedOxidationState.get(), 11);
                world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.1f);
                world.playSound(null, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.25f);
                for (int i = 0; i < 32; i++) {
                    world.addParticle(ParticleTypes.SCRAPE, pos.getX() + 0.5f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), pos.getY() + 0.5f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), pos.getZ() + 0.5f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() / 2.0 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() / 2.0 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() / 2.0 * (double) (random.nextBoolean() ? 1 : -1));
                }
                stackInHand.decrement(1);
                return ActionResult.SUCCESS;
            }
        } else if (BLOCK_SWITCH_MAP.get(state.getBlock()) != null && getPolishingResult(state).isPresent()) {
            world.setBlockState(pos, getPolishingResult(state).get());
            world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.1f);
            world.playSound(null, pos, SoundEvents.BLOCK_CALCITE_BREAK, SoundCategory.BLOCKS, 1.0f, 1.25f);
            for (int i = 0; i < 32; i++) {
                world.addParticle(ParticleTypes.SQUID_INK, pos.getX() + 0.5f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), pos.getY() + 0.5f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), pos.getZ() + 0.5f + random.nextDouble() / 1.5 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() / 16.0 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() / 16.0 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() / 16.0 * (double) (random.nextBoolean() ? 1 : -1));
            }
            stackInHand.decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private Optional<BlockState> getPolishingResult(BlockState state) {
        return getBlockResult(state.getBlock()).map((block) -> block.getStateWithProperties(state));
    }
}
