package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.SeahorseEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class SeahorseMateGoal extends Goal {
    private static final TargetPredicate VALID_MATE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility();
    protected final SeahorseEntity seahorse;
    private final Class<? extends SeahorseEntity> entityClass;
    protected final World world;
    @Nullable
    protected SeahorseEntity mate;
    private int timer;
    private final double speed;

    public SeahorseMateGoal(SeahorseEntity seahorse, double speed, Class<? extends SeahorseEntity> entityClass) {
        this.seahorse = seahorse;
        this.world = seahorse.getWorld();
        this.entityClass = entityClass;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        if (!this.seahorse.isInLove()) {
            return false;
        } else {
            this.mate = this.findMate();
            return this.mate != null;
        }
    }

    public boolean shouldContinue() {
        if (this.mate == null) return false;
        return this.mate.isAlive() && this.mate.isInLove() && this.timer < 60;
    }

    public void stop() {
        this.mate = null;
        this.timer = 0;
    }

    public void tick() {
        this.seahorse.getLookControl().lookAt(this.mate, 10.0F, (float)this.seahorse.getMaxLookPitchChange());
        this.seahorse.getNavigation().startMovingTo(this.mate, this.speed);
        ++this.timer;
        if (this.timer >= this.getTickCount(60) && this.seahorse.squaredDistanceTo(this.mate) < 9.0) {
            this.breed();
        }

    }

    @Nullable
    private SeahorseEntity findMate() {
        List<? extends SeahorseEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.seahorse, this.seahorse.getBoundingBox().expand(8.0));
        double d = Double.MAX_VALUE;
        SeahorseEntity seahorse1 = null;

        for (SeahorseEntity seahorse2 : list) {
            if (this.seahorse.canBreedWith(seahorse2) && this.seahorse.squaredDistanceTo(seahorse2) < d) {
                seahorse1 = seahorse2;
                d = this.seahorse.squaredDistanceTo(seahorse2);
            }
        }

        return seahorse1;
    }

    protected void breed() {
        this.seahorse.breed((ServerWorld)this.world, this.mate);
    }
}
