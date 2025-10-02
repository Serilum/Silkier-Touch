package com.natamus.silkiertouch.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.silkiertouch.events.BlockEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class NeoForgeBlockEvents {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}
		
		BlockEvents.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null);
	}

	@SubscribeEvent
	public static void onEntityBlockPlace(BlockEvent.EntityPlaceEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		Entity entity = e.getEntity();
		if (!(entity instanceof LivingEntity)) {
			return;
		}

		BlockEvents.onEntityBlockPlace(level, e.getPos(), e.getPlacedBlock(), (LivingEntity)entity, null);
	}
}