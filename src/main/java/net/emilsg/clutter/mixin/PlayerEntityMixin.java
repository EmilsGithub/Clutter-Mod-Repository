package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (!(world instanceof ServerWorld)) return;

        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        ItemStack stack = playerEntity.getMainHandStack();
        int greedLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier(Clutter.MOD_ID, "greed")), stack);

        if (greedLevel > 0 && Math.random() < (ModConfigs.GREED_CHANCE_PER_LEVEL * greedLevel)) {
            other.dropItem(ModItems.COMMON_COIN_POUCH);
        }
    }
}
