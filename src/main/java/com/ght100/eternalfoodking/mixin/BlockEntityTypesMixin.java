package com.ght100.eternalfoodking.mixin;

import com.ght100.eternalfoodking.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Set;

@Mixin(BlockEntityTypes.class)
public class BlockEntityTypesMixin {
    @Shadow
    @Final
    public static BlockEntityType<ShulkerBoxBlockEntity> SHULKER_BOX;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addSpaceShulkerBoxesToValidBlocks(CallbackInfo ci) {
        try {
            Field validBlocksField = BlockEntityType.class.getDeclaredField("validBlocks");
            validBlocksField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Set<Block> validBlocks = (Set<Block>) validBlocksField.get(SHULKER_BOX);
            validBlocks.addAll(Blocks.SPACE_SHULKER_BOXES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}