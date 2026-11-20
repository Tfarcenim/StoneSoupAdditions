package tfar.stonesoupadditions.datagen;

import com.mrh0.createaddition.index.CABlocks;
import com.mrh0.createaddition.index.CAItems;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import tfar.stonesoupadditions.StoneSoupAdditions;
import tfar.stonesoupadditions.init.SSABlocks;
import tfar.stonesoupadditions.init.SSAItems;

import java.util.concurrent.CompletableFuture;

public class SSARecipes extends RecipeProvider {
    public SSARecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SSABlocks.HEART_OF_FIRE)
                .define('B', Items.BLAZE_ROD)
                .define('N',Items.NETHERITE_SCRAP)
                .define('n',Items.NETHER_STAR)
                .pattern("BNB")
                .pattern("NnN")
                .pattern("BNB")
                .unlockedBy(getHasName(Items.NETHER_STAR),has(Items.NETHER_STAR))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SSAItems.HALLOWED_FRAGMENT)
                .define('g', Items.GLOWSTONE_DUST)
                .define('N',Items.NETHER_STAR)
                .pattern(" g ")
                .pattern("gNg")
                .pattern(" g ")
                .unlockedBy(getHasName(Items.NETHER_STAR),has(Items.NETHER_STAR))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SSAItems.GS_04)
                .define('E', CABlocks.ELECTRUM_BLOCK)
                .define('P', AllItems.PRECISION_MECHANISM)
                .define('s',CAItems.SPOOL)
                .define('b',AllItems.BRASS_INGOT)
                .pattern("EEP")
                .pattern(" sb")
                .unlockedBy(getHasName(AllItems.PRECISION_MECHANISM),has(AllItems.PRECISION_MECHANISM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SSAItems.DUELING_SHIELD)
                .define('A', Blocks.ANVIL)
                .define('G', Blocks.GOLD_BLOCK)
                .define('s',Items.SHIELD)
                .pattern("AGA")
                .pattern("GsG")
                .pattern(" A ")
                .unlockedBy(getHasName(Blocks.ANVIL),has(Blocks.ANVIL))
                .save(recipeOutput);

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(Items.NETHERITE_SWORD),
                Ingredient.of(Items.VINE),RecipeCategory.MISC,SSAItems.LUSH_SCEPTER)
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_SWORD))
                .save(recipeOutput, StoneSoupAdditions.id("vine_scepter"));
    }
}
