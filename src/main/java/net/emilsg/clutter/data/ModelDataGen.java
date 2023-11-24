package net.emilsg.clutter.data;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool blackOnyxTexturePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BLACK_ONYX_BLOCK);
        BlockStateModelGenerator.BlockTexturePool polishedBlackOnyxTexturePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_BLACK_ONYX);

        blackOnyxTexturePool.slab(ModBlocks.BLACK_ONYX_SLAB);
        blackOnyxTexturePool.stairs(ModBlocks.BLACK_ONYX_STAIRS);
        blackOnyxTexturePool.wall(ModBlocks.BLACK_ONYX_WALL);
        polishedBlackOnyxTexturePool.slab(ModBlocks.POLISHED_BLACK_ONYX_SLAB);
        polishedBlackOnyxTexturePool.stairs(ModBlocks.POLISHED_BLACK_ONYX_STAIRS);
        polishedBlackOnyxTexturePool.wall(ModBlocks.POLISHED_BLACK_ONYX_WALL);

        blockStateModelGenerator.registerCoral(ModBlocks.CUP_CORAL, ModBlocks.DEAD_CUP_CORAL, ModBlocks.CUP_CORAL_BLOCK, ModBlocks.DEAD_CUP_CORAL_BLOCK, ModBlocks.CUP_CORAL_FAN, ModBlocks.DEAD_CUP_CORAL_FAN, ModBlocks.CUP_CORAL_WALL_FAN, ModBlocks.DEAD_CUP_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.GHOST_CORAL, ModBlocks.DEAD_GHOST_CORAL, ModBlocks.GHOST_CORAL_BLOCK, ModBlocks.DEAD_GHOST_CORAL_BLOCK, ModBlocks.GHOST_CORAL_FAN, ModBlocks.DEAD_GHOST_CORAL_FAN, ModBlocks.GHOST_CORAL_WALL_FAN, ModBlocks.DEAD_GHOST_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.STONE_CORAL, ModBlocks.DEAD_STONE_CORAL, ModBlocks.STONE_CORAL_BLOCK, ModBlocks.DEAD_STONE_CORAL_BLOCK, ModBlocks.STONE_CORAL_FAN, ModBlocks.DEAD_STONE_CORAL_FAN, ModBlocks.STONE_CORAL_WALL_FAN, ModBlocks.DEAD_STONE_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.SLATE_CORAL, ModBlocks.DEAD_SLATE_CORAL, ModBlocks.SLATE_CORAL_BLOCK, ModBlocks.DEAD_SLATE_CORAL_BLOCK, ModBlocks.SLATE_CORAL_FAN, ModBlocks.DEAD_SLATE_CORAL_FAN, ModBlocks.SLATE_CORAL_WALL_FAN, ModBlocks.DEAD_SLATE_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.THORN_CORAL, ModBlocks.DEAD_THORN_CORAL, ModBlocks.THORN_CORAL_BLOCK, ModBlocks.DEAD_THORN_CORAL_BLOCK, ModBlocks.THORN_CORAL_FAN, ModBlocks.DEAD_THORN_CORAL_FAN, ModBlocks.THORN_CORAL_WALL_FAN, ModBlocks.DEAD_THORN_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.COCOA_CORAL, ModBlocks.DEAD_COCOA_CORAL, ModBlocks.COCOA_CORAL_BLOCK, ModBlocks.DEAD_COCOA_CORAL_BLOCK, ModBlocks.COCOA_CORAL_FAN, ModBlocks.DEAD_COCOA_CORAL_FAN, ModBlocks.COCOA_CORAL_WALL_FAN, ModBlocks.DEAD_COCOA_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.PASSION_CORAL, ModBlocks.DEAD_PASSION_CORAL, ModBlocks.PASSION_CORAL_BLOCK, ModBlocks.DEAD_PASSION_CORAL_BLOCK, ModBlocks.PASSION_CORAL_FAN, ModBlocks.DEAD_PASSION_CORAL_FAN, ModBlocks.PASSION_CORAL_WALL_FAN, ModBlocks.DEAD_PASSION_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.TOXIC_CORAL, ModBlocks.DEAD_TOXIC_CORAL, ModBlocks.TOXIC_CORAL_BLOCK, ModBlocks.DEAD_TOXIC_CORAL_BLOCK, ModBlocks.TOXIC_CORAL_FAN, ModBlocks.DEAD_TOXIC_CORAL_FAN, ModBlocks.TOXIC_CORAL_WALL_FAN, ModBlocks.DEAD_TOXIC_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.GEM_CORAL, ModBlocks.DEAD_GEM_CORAL, ModBlocks.GEM_CORAL_BLOCK, ModBlocks.DEAD_GEM_CORAL_BLOCK, ModBlocks.GEM_CORAL_FAN, ModBlocks.DEAD_GEM_CORAL_FAN, ModBlocks.GEM_CORAL_WALL_FAN, ModBlocks.DEAD_GEM_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.DIAMOND_CORAL, ModBlocks.DEAD_DIAMOND_CORAL, ModBlocks.DIAMOND_CORAL_BLOCK, ModBlocks.DEAD_DIAMOND_CORAL_BLOCK, ModBlocks.DIAMOND_CORAL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_FAN, ModBlocks.DIAMOND_CORAL_WALL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_WALL_FAN);
        blockStateModelGenerator.registerCoral(ModBlocks.ANCHOR_CORAL, ModBlocks.DEAD_ANCHOR_CORAL, ModBlocks.ANCHOR_CORAL_BLOCK, ModBlocks.DEAD_ANCHOR_CORAL_BLOCK, ModBlocks.ANCHOR_CORAL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_FAN, ModBlocks.ANCHOR_CORAL_WALL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_WALL_FAN);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.SILVER_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.SILVER_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.SILVER_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.SILVER_BOOTS));

        itemModelGenerator.register(ModItems.CAPYBARA_SPAWN_EGG, new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
    }

    private void registerSlab(Block slab, Block fullBlock, TextureMap texture, BlockStateModelGenerator blockStateModelGenerator) { //Credit to Xanthian
        var slabLower = Models.SLAB.upload(slab, texture, blockStateModelGenerator.modelCollector);
        var slabUpper = Models.SLAB_TOP.upload(slab, texture, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab, slabLower, slabUpper, ModelIds.getBlockModelId(fullBlock)));
        blockStateModelGenerator.registerParentedItemModel(slab, slabLower);
    }

    BlockStateSupplier registerWalls(Block block, Block texture, BlockStateModelGenerator blockStateModelGenerator){
        TextureMap textureMap = new TextureMap().put(TextureKey.WALL, TextureMap.getSubId(texture, ""))
                .put(TextureKey.SIDE,  TextureMap.getSubId(texture, ""))
                .put(TextureKey.TOP, TextureMap.getSubId(texture, ""))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(texture, ""));

        Identifier wallPost = Models.TEMPLATE_WALL_POST.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Identifier wallSide = Models.TEMPLATE_WALL_SIDE.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Identifier wallTallSide = Models.TEMPLATE_WALL_SIDE_TALL.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Identifier wallInventory = Models.WALL_INVENTORY.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.registerParentedItemModel(block, wallInventory);
        return BlockStateModelGenerator.createWallBlockState(block, wallPost, wallSide, wallTallSide);
    }
}
