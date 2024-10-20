package net.emilsg.clutter.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.emilsg.clutter.Clutter;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class KilningRecipeBuilder implements CraftingRecipeJsonBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.create();

    public KilningRecipeBuilder(ItemConvertible ingredient, ItemConvertible result, int count) {
        this.ingredient = Ingredient.ofItems(ingredient);
        this.result = result.asItem();
        this.count = count;
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> conditions) {
        this.advancement.criterion(name, conditions);
        return this;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getOutputItem() {
        return result;
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        //this.advancement.parent(new Identifier("recipes/root"))
        //        .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
        //        .rewards(AdvancementRewards.Builder.recipe(recipeId));

        exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId));

        exporter.accept(new JsonBuilder(recipeId, this.result, this.count, this.ingredient,
                this.advancement, new Identifier(recipeId.getNamespace(), "recipes/"
                + recipeId.getPath())));
    }

    public static class JsonBuilder implements RecipeJsonProvider {
        private final Identifier id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;
        private final Advancement.Builder advancement;
        private final Identifier advancementId;

        public JsonBuilder(Identifier id, Item result, int count, Ingredient ingredient, Advancement.Builder advancement, Identifier advancementId) {
            this.id = id;
            this.result = result;
            this.ingredient = ingredient;
            this.count = count;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serialize(JsonObject json) {
            JsonArray jsonarray = new JsonArray();
            jsonarray.add(ingredient.toJson(true));

            json.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registries.ITEM.getId(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            json.add("output", jsonobject);
        }

        @Override
        public Identifier id() {
            Ingredient ingredient = this.ingredient;
            String path = "error";
            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                Identifier id = Registries.ITEM.getId(itemStack.getItem());
                if (id != null) {
                    path = id.getPath();
                }
            }


            return new Identifier(Clutter.MOD_ID, Registries.ITEM.getId(this.result).getPath() + "_from_kilning_" + path);
        }

        @Override
        public RecipeSerializer<?> serializer() {
            return KilningRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public AdvancementEntry advancement() {
            return new AdvancementEntry(id(), advancement.build(id()).value());
        }

    }
}