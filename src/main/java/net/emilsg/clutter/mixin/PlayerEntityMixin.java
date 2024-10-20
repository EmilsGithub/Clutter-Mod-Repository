package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.CopperDivingArmorItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Unique
    private int ticker = 40;

    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (!(world instanceof ServerWorld)) return;

        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        ItemStack stack = playerEntity.getMainHandStack();
        int greedLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier(Clutter.MOD_ID, "greed")), stack);

        if (greedLevel > 0 && Math.random() < (ModConfigManager.get(Configs.greedChancePerLevel, 0.01f) * greedLevel)) {
            ItemStack droppedStack = new ItemStack(ModItems.COIN_POUCH);
            NbtCompound nbt = droppedStack.getOrCreateNbt();
            nbt.putInt("Rarity", 0);
            other.dropStack(droppedStack);
        }
    }

    @Inject(at = @At("HEAD"), method = "isSwimming", cancellable = true)
    private void updateSwimmingMixin(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        ItemStack equippedBoots = playerEntity.getEquippedStack(EquipmentSlot.FEET);

        if ((equippedBoots.getItem() instanceof CopperDivingArmorItem)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    private void movementTick(CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        Iterable<ItemStack> armorItems = playerEntity.getArmorItems();

        ItemStack equippedHead = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack equippedChest = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack equippedBoots = playerEntity.getEquippedStack(EquipmentSlot.FEET);

        if (playerEntity.isInsideWaterOrBubbleColumn() && !playerEntity.isSpectator() && !playerEntity.getAbilities().creativeMode) {

            if (equippedBoots.getItem() instanceof CopperDivingArmorItem) {
                playerEntity.addVelocity(0, playerEntity.isSneaking() ? -0.1f : -0.05f, 0);
                playerEntity.setStepHeight(1.0F);
            }

            if (equippedChest.getItem() instanceof CopperDivingArmorItem && equippedHead.getItem() instanceof CopperDivingArmorItem &&
                    (equippedHead.getDamage() < equippedHead.getMaxDamage() - 1) && (equippedChest.getDamage() < equippedChest.getMaxDamage() - 1)) {
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 100, 0, false, false));
                if (ticker <= 0) {
                    equippedChest.damage(1, playerEntity, null);
                    ticker = 40;
                }
                ticker--;
            }
        } else {
            playerEntity.setStepHeight(0.6F);
        }
    }
}
