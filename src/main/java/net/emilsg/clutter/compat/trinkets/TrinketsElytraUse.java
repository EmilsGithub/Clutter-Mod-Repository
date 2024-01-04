package net.emilsg.clutter.compat.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.emilsg.clutter.util.ModItemTags;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Pair;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrinketsElytraUse {

    public static void doFlight() {
        EntityElytraEvents.CUSTOM.register((LivingEntity livingEntity, boolean tick) -> {
            for (ItemStack itemStack : getEquippedElytra(livingEntity)) {
                if(elytraIsUsable(livingEntity, itemStack, tick)) return true;
            }

            return false;
        });
    }

    public static List<ItemStack> getEquippedElytra(LivingEntity livingEntity) {

        List<ItemStack> out = new ArrayList<>();

        Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(livingEntity);

        if(optional.isEmpty()) return out;

        TrinketComponent trinketComponent = optional.get();

        TagKey<Item> elytronTag = ModItemTags.ELYTRON;

        for (Pair<SlotReference, ItemStack> pair : trinketComponent.getAllEquipped()) {
            ItemStack itemStack = pair.getRight();

            if (itemStack.isIn(elytronTag)) {

                if (pair.getLeft().inventory().getSlotType().getName().equals("cape")) out.add(itemStack);

            }
        }

        return out;
    }

    public static boolean elytraIsUsable(LivingEntity entity, ItemStack itemStack, boolean tickElytra) {
        if (itemStack.getDamage() < itemStack.getMaxDamage() - 1) {
            if (tickElytra) doElytraTick(entity, itemStack);

            return true;
        }

        return false;
    }

    private static void doElytraTick(LivingEntity entity, ItemStack itemStack) {
        int nextRoll = entity.getRoll() + 1;

        if (!entity.getWorld().isClient && nextRoll % 10 == 0) {
            if ((nextRoll / 10) % 2 == 0) itemStack.damage(1, entity, null);

            entity.emitGameEvent(GameEvent.ELYTRA_GLIDE);
        }
    }
}
