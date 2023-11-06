package net.emilsg.clutter.data;

import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider {
    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /** Vanilla **/

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.SILVER_HELMET, ModItems.SILVER_CHESTPLATE, ModItems.SILVER_LEGGINGS, ModItems.SILVER_BOOTS);

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ONYX, ModItems.SILVER_INGOT);

        /** Modded **/


        /** Common **/
    }
}
