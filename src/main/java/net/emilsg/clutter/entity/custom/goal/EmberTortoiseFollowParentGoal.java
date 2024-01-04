package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.passive.AnimalEntity;

public class EmberTortoiseFollowParentGoal extends FollowParentGoal {
    AnimalEntity animalEntity;

    public EmberTortoiseFollowParentGoal(AnimalEntity animal, double speed) {
        super(animal, speed);
        this.animalEntity = animal;
    }

    @Override
    public boolean canStart() {
        if(animalEntity instanceof EmberTortoiseEntity emberTortoise) return !emberTortoise.isShielding() && super.canStart();
        else return super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if(animalEntity instanceof EmberTortoiseEntity emberTortoise) {
            if (emberTortoise.isShielding()) stop();
        }
    }
}
