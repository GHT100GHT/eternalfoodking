package com.ght100.eternalfoodking.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EternalfoodkingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");
        String s = LocalDateTime.now().format(fmt);
        if(s.equals("12-13")){
            if( Minecraft.getInstance().getLanguageManager().getSelected().equals("zh_cn")){

            }
        }
	}
}