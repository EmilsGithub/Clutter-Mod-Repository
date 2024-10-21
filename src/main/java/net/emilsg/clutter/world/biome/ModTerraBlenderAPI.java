package net.emilsg.clutter.world.biome;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.world.biome.surface.ModMaterialRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerraBlenderAPI implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(Identifier.of(Clutter.MOD_ID, "overworld"), 3));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Clutter.MOD_ID, ModMaterialRules.makeRules());
    }
}
