package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.passive.AnimalEntity;

public class EmberTortoiseLookAtEntityGoal extends LookAtEntityGoal {
    AnimalEntity animalEntity;

    public EmberTortoiseLookAtEntityGoal(AnimalEntity animal, Class<? extends LivingEntity> targetType, float range) {
        super(animal, targetType, range);
        this.animalEntity = animal;
    }

    @Override
    public boolean canStart() {
        if(animalEntity instanceof EmberTortoiseEntity emberTortoise) return !emberTortoise.isShielding() && super.canStart();
        else return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        if(animalEntity instanceof EmberTortoiseEntity emberTortoise && emberTortoise.isShielding()) return false;
        return this.target != null && super.shouldContinue();
    }

    @Override
    public void tick() {
        super.tick();
        if(animalEntity instanceof EmberTortoiseEntity emberTortoise) {
            if (emberTortoise.isShielding()) stop();
        }
    }
}
