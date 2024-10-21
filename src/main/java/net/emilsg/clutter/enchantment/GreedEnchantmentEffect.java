package net.emilsg.clutter.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Vec3d;

public record GreedEnchantmentEffect(int level) implements EnchantmentEntityEffect {

    public static final MapCodec<GreedEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.INT.fieldOf("level").forGetter(GreedEnchantmentEffect::level))
            .apply(instance, GreedEnchantmentEffect::new));

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (!(target.getWorld() instanceof ServerWorld serverWorld)) return;

        if (!(target instanceof LivingEntity livingTarget) || target.getWorld().isClient()) return;

        boolean targetIsAlive = livingTarget.isAlive();

        if (!targetIsAlive && level > 0 && Math.random() < (ModConfigManager.get(Configs.greedChancePerLevel, 0.01f) * level)) {
            ItemStack droppedStack = new ItemStack(ModItems.COIN_POUCH);
            droppedStack.set(DataComponentTypes.RARITY, Rarity.COMMON);
            ItemEntity coinItemEntity = new ItemEntity(serverWorld, pos.x, pos.y, pos.z, droppedStack);
            serverWorld.spawnEntity(coinItemEntity);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
