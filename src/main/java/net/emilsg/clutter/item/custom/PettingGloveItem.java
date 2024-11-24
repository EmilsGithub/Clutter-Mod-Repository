package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.easter_egg.NameOnPet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class PettingGloveItem extends Item {

    public PettingGloveItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();

        if (entity instanceof LivingEntity && !user.getItemCooldownManager().isCoolingDown(this)) {
            LivingEntity finalClosestEntity = NameOnPet.giveName(entity, user, world);
            ParticleEffect particleEffect = ParticleTypes.HEART;

            if (finalClosestEntity instanceof PassiveEntity passiveEntity) {
                passiveEntity.playAmbientSound();
            }

            if (world instanceof ServerWorld serverWorld && finalClosestEntity.getHealth() < finalClosestEntity.getMaxHealth() && serverWorld.random.nextInt(10) == 0) {
                finalClosestEntity.heal(0.5f);
                user.getItemCooldownManager().set(this, 60);
                stack.damage(1, user, LivingEntity.getSlotForHand(hand));
            }

            Box hitbox = finalClosestEntity.getBoundingBox();
            double hitBoxWidth = hitbox.getLengthX();
            double hitBoxHeight = hitbox.getLengthY();
            double hitBoxDepth = hitbox.getLengthZ();

            for (int i = 0; i < (int) (10 * finalClosestEntity.getWidth()); i++) {
                Random random = new Random();

                double offsetX = hitbox.minX + (hitBoxWidth * random.nextDouble());
                double offsetY = hitbox.minY + (hitBoxHeight * random.nextDouble());
                double offsetZ = hitbox.minZ + (hitBoxDepth * random.nextDouble());

                world.addParticle(particleEffect, offsetX, offsetY, offsetZ, 1, 0, 0);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.clutter.petting_glove.tooltip").formatted(Formatting.BLUE));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
