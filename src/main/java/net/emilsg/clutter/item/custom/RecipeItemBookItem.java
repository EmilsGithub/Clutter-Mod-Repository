package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.Clutter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecipeItemBookItem extends DescriptionItem {
    public RecipeItemBookItem(Settings settings, String description, Formatting formatting) {
        super(settings, description, formatting);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            unlockAllRecipesFromNamespace((ServerPlayerEntity) user, Clutter.MOD_ID);
            world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS);
        }
        user.swingHand(hand);
        return super.use(world, user, hand);
    }

    public void unlockAllRecipesFromNamespace(ServerPlayerEntity player, String namespace) {
        List<RecipeEntry<?>> recipes = Objects.requireNonNull(player.getServer()).getRecipeManager().values().stream()
        .filter(recipe -> recipe.id().getNamespace().equals(namespace)).collect(Collectors.toList());
        if (!recipes.isEmpty()) {
            player.unlockRecipes(recipes);
        }
    }
}
