package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.SeahorseEntity;
import net.minecraft.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeahorseFollowParentGoal extends Goal {
    public static final int HORIZONTAL_CHECK_RANGE = 8;
    public static final int VERTICAL_CHECK_RANGE = 4;
    public static final int MIN_DISTANCE = 3;
    private final SeahorseEntity seahorse;
    @Nullable
    private SeahorseEntity parent;
    private final double speed;
    private int delay;

    public SeahorseFollowParentGoal(SeahorseEntity seahorse, double speed) {
        this.seahorse = seahorse;
        this.speed = speed;
    }

    public boolean canStart() {
        if (this.seahorse.getBreedingAge() >= 0) {
            return false;
        } else {
            List<? extends SeahorseEntity> list = this.seahorse.getWorld().getNonSpectatingEntities(this.seahorse.getClass(), this.seahorse.getBoundingBox().expand(8.0, 4.0, 8.0));
            SeahorseEntity seahorseEntity = null;
            double d = Double.MAX_VALUE;

            for (SeahorseEntity seahorseEntity1 : list) {
                if (seahorseEntity1.getBreedingAge() >= 0) {
                    double e = this.seahorse.squaredDistanceTo(seahorseEntity1);
                    if (!(e > d)) {
                        d = e;
                        seahorseEntity = seahorseEntity1;
                    }
                }
            }

            if (seahorseEntity == null) {
                return false;
            } else if (d < 9.0) {
                return false;
            } else {
                this.parent = seahorseEntity;
                return true;
            }
        }
    }

    public boolean shouldContinue() {
        if (this.seahorse.getBreedingAge() >= 0) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double d = this.seahorse.squaredDistanceTo(this.parent);
            return !(d < 9.0) && !(d > 256.0);
        }
    }

    public void start() {
        this.delay = 0;
    }

    public void stop() {
        this.parent = null;
    }

    public void tick() {
        if (--this.delay <= 0) {
            this.delay = this.getTickCount(10);
            this.seahorse.getNavigation().startMovingTo(this.parent, this.speed);
        }
    }
}
