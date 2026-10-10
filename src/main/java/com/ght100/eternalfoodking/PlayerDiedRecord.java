package com.ght100.eternalfoodking;

import net.minecraft.core.BlockPos;

public class PlayerDiedRecord {
    private final String level;
    private final BlockPos pos;

    public String getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public PlayerDiedRecord
            (String s){
        String[] parts = s.split("#", 2);
        level = parts[0];
        String value = parts[1];
        pos= BlockPos.of(Long.parseLong(value));
    }
    public PlayerDiedRecord
            (String level,BlockPos pos){
        this. level=level;
        this.pos=pos;
    }
    @Override
    public String toString(){
        return level+"#"+String.valueOf( pos.asLong());
    }

}
