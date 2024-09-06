package net.emilsg.clutter.entity.custom.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public class EmperorPenguinConditionalLookAroundGoal extends LookAroundGoal {
    protected final MobEntity mob;

    public EmperorPenguinConditionalLookAroundGoal(MobEntity mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !mob.isSubmergedInWater();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !mob.isSubmergedInWater();
    }
}
