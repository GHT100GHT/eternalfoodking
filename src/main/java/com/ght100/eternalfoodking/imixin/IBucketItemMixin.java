package com.ght100.eternalfoodking.imixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.ght100.eternalfoodking.Items.DOG_MILK_BUCKET;

public interface IBucketItemMixin {
    default void IonUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() != Items.BUCKET) return;

        double range = 5.0;
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endVec = eyePos.add(lookVec.scale(range));
        AABB aabb = player.getBoundingBox().inflate(range);
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(
                level,
                player,
                eyePos,
                endVec,
                aabb,
                e -> e instanceof Wolf && !e.isSpectator(),
                0.0f
        );
        if (hit == null) return;
        Entity target = hit.getEntity();
        if (target instanceof Wolf wolf && !wolf.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

            boolean isCreative = player.hasInfiniteMaterials();
            ItemStack result;

            if (!isCreative) {
                stack.shrink(1);
            }
            ItemStack milkStack = new ItemStack(DOG_MILK_BUCKET);

            if (stack.isEmpty()) {
                result = milkStack;
            } else {
                if (!player.getInventory().add(milkStack)) {
                    player.drop(milkStack, false);
                }
                result = stack;
            }

            cir.setReturnValue(InteractionResult.SUCCESS.heldItemTransformedTo(result));
        }
    }
}
