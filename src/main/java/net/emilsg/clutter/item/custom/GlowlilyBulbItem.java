package net.emilsg.clutter.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GlowlilyBulbItem extends Item {

    public GlowlilyBulbItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
        return super.finishUsing(stack, world, user);
    }
}
