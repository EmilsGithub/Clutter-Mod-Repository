package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.SeahorseEntity;
import net.emilsg.clutter.entity.variants.SeahorseVariant;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class SeahorseReleaseChildrenGoal extends Goal {
    private final SeahorseEntity seahorse;

    public SeahorseReleaseChildrenGoal(SeahorseEntity seahorse) {
        this.seahorse = seahorse;
    }

    @Override
    public boolean canStart() {
        return this.areChildrenReady();
    }

    @Override
    public boolean shouldContinue() {
        return this.areChildrenReady();
    }

    @Override
    public void start() {
        if (seahorse.getWorld() instanceof ServerWorld serverWorld) {
            Random random = serverWorld.getRandom();
            List<SeahorseEntity> children = new ArrayList<>();

            for (int i = 0; i < random.nextInt(seahorse.getMaxChildren()) + 1; i++) {
                SeahorseEntity child = seahorse.createChild(serverWorld, seahorse);
                if (child == null) return;
                child.setBaby(true);
                child.setPosition(seahorse.getPos());
                child.setVariant(random.nextBoolean() ? seahorse.getVariant() : Util.getRandom(SeahorseVariant.values(), serverWorld.random));
                children.add(child);
            }

            for (SeahorseEntity spawnedChild : children) {
                serverWorld.spawnEntity(spawnedChild);
            }

            seahorse.setHasChildren(false);
            seahorse.setHasChildrenTimer(0.0f);
        }
    }

    private boolean areChildrenReady() {
        return seahorse.getHasChildrenTimer() >= 0.35;
    }
}
