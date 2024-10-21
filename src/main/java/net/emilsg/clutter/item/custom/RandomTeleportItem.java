package net.emilsg.clutter.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RandomTeleportItem extends Item {
    private final int cooldownInTicks;
    private final int useTimeInTicks;
    private final int maxTeleportDistance;

    public RandomTeleportItem(Settings settings, int cooldownInTicks, int useTimeInTicks, int maxTeleportDistance) {
        super(settings);
        this.useTimeInTicks = useTimeInTicks;
        this.cooldownInTicks = cooldownInTicks;
        this.maxTeleportDistance = maxTeleportDistance;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (!world.isClient) {
            double d = user.getX();
            double e = user.getY();
            double f = user.getZ();

            for (int i = 0; i < 16; ++i) {
                double g = user.getX() + (user.getRandom().nextDouble() - 0.5) * maxTeleportDistance;
                double h = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt(16) - 8), world.getBottomY(), world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1);
                double j = user.getZ() + (user.getRandom().nextDouble() - 0.5) * maxTeleportDistance;
                if (user.hasVehicle()) {
                    user.stopRiding();
                }

                Vec3d vec3d = user.getPos();
                if (user.teleport(g, h, j, true)) {
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                    SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound(null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    user.playSound(soundEvent, 1.0F, 1.0F);
                    break;
                }
            }
            if (user instanceof PlayerEntity) {
                ((PlayerEntity) user).getItemCooldownManager().set(this, cooldownInTicks);
            }
        }

        return itemStack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return useTimeInTicks;
    }
}
