package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;

public class EmberTortoiseMeleeGoal extends MeleeAttackGoal {
    private final EmberTortoiseEntity entity;
    private final double speed;
    private int attackDelay = 10;
    private Path path;
    private int updateCountdownTicks;
    private int cooldown;
    private long lastUpdateTime;
    private int ticksUntilNextAttack = 10;
    private boolean shouldCountTillNextAttack = false;

    public EmberTortoiseMeleeGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((EmberTortoiseEntity) mob);
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        return !entity.isShielding() && startAttack();
    }

    private boolean startAttack() {
        long l = this.mob.getWorld().getTime();
        if (l - this.lastUpdateTime < 20L) {
            return false;
        }
        this.lastUpdateTime = l;
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!livingEntity.isAlive()) {
            return false;
        }
        this.path = this.mob.getNavigation().findPathTo(livingEntity, 1);
        if (this.path != null) {
            return true;
        }

        return this.isEnemyWithinAttackDistance(livingEntity);
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingAlong(this.path, this.speed);
        this.mob.setAttacking(true);
        this.updateCountdownTicks = 0;
        this.cooldown = 0;
        attackDelay = 10;
        ticksUntilNextAttack = 10;
    }

    @Override
    protected void attack(LivingEntity target) {
        if (this.entity.isShielding()) {
            entity.getNavigation().stop();
            resetAttackCooldown();
            entity.setAttacking(false);
            return;
        }
        if (isEnemyWithinAttackDistance(target)) {
            shouldCountTillNextAttack = true;

            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if (isTimeToAttack()) {
                this.mob.getLookControl().lookAt(target.getX(), target.getEyeY(), target.getZ());
                performAttack(target);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy) {
        return this.mob.squaredDistanceTo(pEnemy) < 5f;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2);
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(pEnemy);
    }

    @Override
    public void tick() {
        if (this.entity.isShielding()) {
            resetAttackCooldown();
            entity.setAttacking(false);
            this.stop();
            return;
        }
        super.tick();

        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
