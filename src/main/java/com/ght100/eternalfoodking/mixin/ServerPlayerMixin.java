package com.ght100.eternalfoodking.mixin;

import com.ght100.eternalfoodking.PlayerDiedRecord;
import com.ght100.eternalfoodking.imixin.IDiedPlayerRecord;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Stack;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements IDiedPlayerRecord {
    @Inject(method = "die", at = @At(value = "RETURN"), remap = false)
    void onDie(DamageSource source, CallbackInfo ci){
        addDiedRecord(new PlayerDiedRecord (((Player)(Object)this).level().dimensionTypeRegistration().getRegisteredName(),BlockPos.containing(((Player)(Object)this).position())));
    }
    @Unique
    private final Stack<PlayerDiedRecord> diedRecord=new Stack<>();
    @Override
    public Stack<PlayerDiedRecord> getDiedRecord() {
        return diedRecord;
    }

    @Override
    public void addDiedRecord(PlayerDiedRecord pos) {
        diedRecord.add(pos);
    }
    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"),remap = false)
    private void onWriteNbt(ValueOutput output, CallbackInfo ci) {
        ValueOutput.TypedOutputList<String> listOutput = output.list("DiedRecord", Codec.STRING);

        for (PlayerDiedRecord pos : diedRecord) {
            listOutput.add(pos.toString());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"),remap = false)
    private void onReadNbt(ValueInput input, CallbackInfo ci) {
        diedRecord.clear();


        List<String> positions = input.read("DiedRecord", Codec.STRING.listOf()).orElse(List.of());
        for (String pos : positions) {
            diedRecord.add(new PlayerDiedRecord(pos));
            if (diedRecord.size() >= 100) break;
        }
    }
}
