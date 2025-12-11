package com.natamus.silkiertouch;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.silkiertouch.events.BlockEvents;
import com.natamus.silkiertouch.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ModFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, entity) -> {
			return BlockEvents.onBlockBreak(level, player, pos, state, entity);
		});

		CollectiveBlockEvents.BLOCK_PLACE.register((level, blockPos, blockState, livingEntity, itemStack) -> {
			return BlockEvents.onEntityBlockPlace(level, blockPos, blockState, livingEntity, itemStack);
		});
	}

	private static void setGlobalConstants() {

	}
}
