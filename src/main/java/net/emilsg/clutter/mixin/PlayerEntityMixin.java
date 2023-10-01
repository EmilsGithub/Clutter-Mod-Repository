package net.emilsg.clutter.mixin;

import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    int ticker = 0;

    @Shadow public abstract boolean isPlayer();


    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        ItemStack stack = playerEntity.getMainHandStack();
        int greedLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier("clutter", "greed")), stack);

        if (greedLevel > 0 && Math.random() < (ModConfigs.GREED_CHANCE_PER_LEVEL * greedLevel)) {
            other.dropItem(ModItems.COMMON_COIN_POUCH);
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ticker++;
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        ItemStack headStack = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
        if(headStack.isOf(ModItems.VIKING_HELMET) && ticker >= 40) {
            if (playerEntity.getHealth() < (playerEntity.getMaxHealth() / 2)) {
                if(Objects.requireNonNull(playerEntity.getStatusEffect(ModEffects.VULNERABILITY)).isDurationBelow(100)) playerEntity.removeStatusEffect(ModEffects.VULNERABILITY);
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0, false, true, true));
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0, false, true, true));
                ticker = 0;
            } else {
                playerEntity.addStatusEffect(new StatusEffectInstance(ModEffects.VULNERABILITY, 100, 0, false, true, true));
                ticker = 0;
            }
        }
    }
}
