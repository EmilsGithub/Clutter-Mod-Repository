package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;

public class MossbloomDropHornsGoal extends Goal {
    private final MossbloomEntity mossbloom;
    private final ItemStack antlerStack = new ItemStack(ModItems.MOSSBLOOM_ANTLER);
    private boolean hasDroppedAntlers = false;

    public MossbloomDropHornsGoal(MossbloomEntity mossbloom) {
        this.mossbloom = mossbloom;
        this.antlerStack.setCount(2);
    }

    @Override
    public boolean canStart() {
        return this.mossbloom.getHasHorns() && this.mossbloom.getHornDropTimer() > MossbloomEntity.SHOULD_DROP_HORNS_VALUE;
    }

    @Override
    public void start() {
        this.mossbloom.setIsShaking(true);
        this.mossbloom.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.mossbloom.setTimeTillDrop(this.mossbloom.getTimeTillDrop() + 1);

        if (this.mossbloom.getTimeTillDrop() >= 60) {
            this.mossbloom.setHornDropTimer(0);
            this.mossbloom.setHasHorns(false);
            this.mossbloom.dropStack(antlerStack);
            this.hasDroppedAntlers = true;
            this.mossbloom.setIsShaking(false);
            this.mossbloom.shakingAnimationState.stop();
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return this.mossbloom.getHasHorns() && this.mossbloom.getHornDropTimer() > MossbloomEntity.SHOULD_DROP_HORNS_VALUE && !this.hasDroppedAntlers;
    }

    @Override
    public void stop() {
        this.hasDroppedAntlers = false;
        this.mossbloom.setTimeTillDrop(0);
    }
}
