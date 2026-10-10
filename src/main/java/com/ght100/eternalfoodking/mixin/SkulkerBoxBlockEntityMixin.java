package com.ght100.eternalfoodking.mixin;

import com.ght100.eternalfoodking.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxBlockEntity.class)
public class SkulkerBoxBlockEntityMixin {
    @Inject(method = "canPlaceItemThroughFace", at = @At("HEAD"), cancellable = true)
    private void canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction, CallbackInfoReturnable<Boolean> cir) {
        ShulkerBoxBlockEntity entity = (ShulkerBoxBlockEntity) (Object) this;
        if (Blocks.SPACE_SHULKER_BOXES.contains(entity.getBlockState().getBlock())) {
            cir.setReturnValue(true);
        }
    }

}
