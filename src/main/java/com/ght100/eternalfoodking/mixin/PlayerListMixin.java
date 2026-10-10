package com.ght100.eternalfoodking.mixin;

import com.ght100.eternalfoodking.PlayerDiedRecord;
import com.ght100.eternalfoodking.imixin.IDiedPlayerRecord;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Stack;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "respawn", at = @At("RETURN"),remap = false)
    private void onRespawn(ServerPlayer serverPlayer, boolean keepAllPlayerData, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayer> cir) {
        ServerPlayer newPlayer = cir.getReturnValue();
        IDiedPlayerRecord oldRecord = (IDiedPlayerRecord) serverPlayer;
        IDiedPlayerRecord newRecord = (IDiedPlayerRecord) newPlayer;


        Stack<PlayerDiedRecord> oldStack = oldRecord.getDiedRecord();
        if (oldStack != null && !oldStack.isEmpty()) {
            Stack<PlayerDiedRecord> newStack = newRecord.getDiedRecord();
            newStack.clear();
            newStack.addAll(oldStack);
        }
    }
}
