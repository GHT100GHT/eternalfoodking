package com.ght100.eternalfoodking;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.IdMapper;
import net.minecraft.resources.Identifier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Eternalfoodking implements ModInitializer {
	public static final String MOD_ID = "eternalfoodking";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        Items.registerAll();
        Blocks.registerAll();
        addBlockStatesToIdMap();

	}
    private static void addBlockStatesToIdMap() {
        try {
            Field field = Block.class.getDeclaredField("BLOCK_STATE_REGISTRY");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            IdMapper<BlockState> idMapper = (IdMapper<BlockState>) field.get(null);
            for (Block block : Blocks.SPACE_SHULKER_BOXES) {
                for (BlockState state : block.getStateDefinition().getPossibleStates()) {

                    if (idMapper.getId(state) == -1) {
                        idMapper.add(state);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add custom block states to network ID map", e);
        }
    }
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
