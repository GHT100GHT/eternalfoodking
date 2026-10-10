package com.ght100.eternalfoodking.imixin;

import com.ght100.eternalfoodking.PlayerDiedRecord;
import net.minecraft.core.BlockPos;

import java.util.Stack;

public interface IDiedPlayerRecord {

    Stack<PlayerDiedRecord> getDiedRecord();
    void addDiedRecord(PlayerDiedRecord pos);

}
