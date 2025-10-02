package com.natamus.silkiertouch.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.silkiertouch.events.BlockEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeBlockEventss {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeBlockEventss.class);
	}

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