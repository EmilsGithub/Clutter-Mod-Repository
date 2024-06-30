package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.recipe.Ingredient;

public class EmberTortoiseTemptGoal extends TemptGoal {
    EmberTortoiseEntity emberTortoise;

    public EmberTortoiseTemptGoal(EmberTortoiseEntity emberTortoise, double speed, Ingredient food, boolean canBeScared) {
        super(emberTortoise, speed, food, canBeScared);
        this.emberTortoise = emberTortoise;
    }

    @Override
    public boolean canStart() {
        return !this.emberTortoise.isShielding() && super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.emberTortoise.isShielding()) stop();
    }
}
