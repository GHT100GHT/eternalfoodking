package com.ght100.eternalfoodking;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.*;

public class Blocks {
    public static final Set<Block> SPACE_SHULKER_BOXES =new HashSet<>();

    static {
        for (DyeColor color : DyeColor.values()) {
            String name = color.getName() + "_space_shulker_box";
            Block block = new ShulkerBoxBlock(color,
                    BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SHULKER_BOX).setId(getBlockID(name))
            );

            Block registered =register(
                    name,

                    block,1
            );



            SPACE_SHULKER_BOXES.add(registered);
        }
    }
    public static ResourceKey<Block> getBlockID(String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Eternalfoodking.MOD_ID, id));
    }

    public static Block register(String id, Block block,Item.Properties properties) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(Eternalfoodking.MOD_ID, id),
                new BlockItem(block, properties.setId(Items.getItemID(id))));
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(Eternalfoodking.MOD_ID, id), block);
    }
    public static Block register(String id ,Block block,int maxcount){
        return register(id ,block,new Item.Properties().stacksTo(maxcount));
    }

    public static void registerAll() {
    }
}
