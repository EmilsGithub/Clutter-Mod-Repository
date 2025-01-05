package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class BeerItem extends BlockItem {

    public BeerItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity;
        PlayerEntity playerEntity2 = playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
        }
        if (!world.isClient) {
            extendOrApplyEffect(user, ModEffects.VULNERABILITY, 300, 0);
            extendOrApplyEffect(user, StatusEffects.STRENGTH, 300, 0);

            boolean shouldApplyNausea = (user.hasStatusEffect(ModEffects.VULNERABILITY) && Objects.requireNonNull(user.getStatusEffect(ModEffects.VULNERABILITY)).getDuration() > 600)
                    || (user.hasStatusEffect(StatusEffects.STRENGTH) && Objects.requireNonNull(user.getStatusEffect(StatusEffects.STRENGTH)).getDuration() > 600);

            if (shouldApplyNausea) {
                extendOrApplyEffect(user, StatusEffects.NAUSEA, 300, 0);
            }

            if (user instanceof PlayerEntity) {
                ((PlayerEntity) user).getHungerManager().add(4, 0.5F);
            }
        }
        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.WOODEN_MUG);
            }
            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(ModItems.WOODEN_MUG));
            }
        }
        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    private void extendOrApplyEffect(LivingEntity user, RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        StatusEffectInstance currentEffect = user.getStatusEffect(effect);

        if (currentEffect != null) {
            int newDuration = currentEffect.getDuration() + duration;
            user.addStatusEffect(new StatusEffectInstance(effect, newDuration, amplifier));
        } else {
            user.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }


    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}