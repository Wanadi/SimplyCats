package com.github.mnesikos.simplycats.init;

import com.github.mnesikos.simplycats.block.BlockBowl;
import com.github.mnesikos.simplycats.block.BlockCropCatnip;
import com.github.mnesikos.simplycats.block.BlockLitterBox;
import com.github.mnesikos.simplycats.block.BlockScratchingPost;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
    public static BlockCropCatnip CROP_CATNIP = new BlockCropCatnip("crop_catnip");
    public static BlockBowl BOWL = new BlockBowl("cat_bowl");
    public static BlockLitterBox LITTER_BOX = new BlockLitterBox("litter_box");
    public static BlockScratchingPost SCRATCHING_POST = new BlockScratchingPost("scratching_post");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                CROP_CATNIP,
                BOWL,
                LITTER_BOX,
                SCRATCHING_POST
        );

        GameRegistry.registerTileEntity(BOWL.getTileEntityClass(), new ResourceLocation(BOWL.getRegistryName().toString()));
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                BOWL.createItemBlock(),
                LITTER_BOX.createItemBlock(),
                SCRATCHING_POST.createItemBlock()
        );
    }

    public static void registerModels() {
        BOWL.registerItemModel(Item.getItemFromBlock(BOWL));
        LITTER_BOX.registerItemModel(Item.getItemFromBlock(LITTER_BOX));
        SCRATCHING_POST.registerItemModel(Item.getItemFromBlock(SCRATCHING_POST));
    }
}
