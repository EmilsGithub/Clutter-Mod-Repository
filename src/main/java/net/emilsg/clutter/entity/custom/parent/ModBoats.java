package net.emilsg.clutter.entity.custom.parent;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModBoats {
    public static final Identifier REDWOOD_BOAT_ID = Identifier.of(Clutter.MOD_ID, "redwood_boat");
    public static final Identifier REDWOOD_CHEST_BOAT_ID = Identifier.of(Clutter.MOD_ID, "redwood_chest_boat");

    public static final RegistryKey<TerraformBoatType> REDWOOD_BOAT_KEY = TerraformBoatTypeRegistry.createKey(REDWOOD_BOAT_ID);

    public static void registerBoats() {
        TerraformBoatType redwoodBoat = new TerraformBoatType.Builder()
                .item(ModItems.REDWOOD_BOAT)
                .chestItem(ModItems.REDWOOD_CHEST_BOAT)
                .planks(ModBlocks.REDWOOD_PLANKS.asItem())
                .build();

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, REDWOOD_BOAT_KEY, redwoodBoat);
    }

}
