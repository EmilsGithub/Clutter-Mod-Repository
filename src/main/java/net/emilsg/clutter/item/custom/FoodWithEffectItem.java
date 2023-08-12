package net.emilsg.clutter.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FoodWithEffectItem extends Item {
    private final StatusEffect effect;
    private final int duration;
    private final int amplifier;
    private final int useTimeInTicks;
    private final int cooldownInTicks;

    public FoodWithEffectItem(Settings settings, StatusEffect effect, int duration, int amplifier, int useTimeInTicks, int cooldownInTicks) {
        super(settings);
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.useTimeInTicks = useTimeInTicks;
        this.cooldownInTicks = cooldownInTicks;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        if (user instanceof PlayerEntity) {
            ((PlayerEntity) user).getItemCooldownManager().set(this, cooldownInTicks);
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return (int) useTimeInTicks;
    }
}
