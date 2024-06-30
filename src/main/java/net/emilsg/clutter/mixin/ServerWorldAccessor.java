package net.emilsg.clutter.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {
    @Accessor("spawners")
    List<Spawner> getWorldSpawners();

    @Accessor("spawners")
    @Final
    @Mutable
    void setWorldSpawners(List<Spawner> spawners);
}
