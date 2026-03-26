package com.natamus.silkiertouch.events;

import com.natamus.collective.functions.ItemFunctions;
import com.natamus.collective.functions.TaskFunctions;
import com.natamus.collective.services.Services;
import com.natamus.silkiertouch.config.ConfigHandler;
import com.natamus.silkiertouch.mixin.CandleCakeBlockAccessor;
import com.natamus.silkiertouch.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;

public class BlockEvents {
	public static boolean onBlockBreak(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
		if (level.isClientSide()) {
			return true;
		}

		if (player.isCreative()) {
			return true;
		}

		InteractionHand interactionHand = InteractionHand.MAIN_HAND;
		ItemStack handStack = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (!Util.hasSilkTouch(level, handStack)) {
			interactionHand = InteractionHand.OFF_HAND;
			handStack = player.getItemInHand(InteractionHand.OFF_HAND);
			if (!Util.hasSilkTouch(level, handStack)) {
				return true;
			}
		}

		boolean isPickaxe = Services.TOOLFUNCTIONS.isPickaxe(handStack);

		Block block = blockState.getBlock();

		ItemStack outStack = null;
		if (((ConfigHandler.enableSpawnerDrop && block.equals(Blocks.SPAWNER)) || (ConfigHandler.enableTrialSpawnerDrop && block.equals(Blocks.TRIAL_SPAWNER))) && isPickaxe) {
			if (blockEntity == null) {
				blockEntity = level.getBlockEntity(blockPos);
			}

			Entity spawnerDisplayEntity = null;
			CompoundTag spawnerData = null;

			if (blockEntity instanceof SpawnerBlockEntity spawnerBlockEntity) {
				spawnerDisplayEntity = spawnerBlockEntity.getSpawner().getOrCreateDisplayEntity(level, blockPos);
				spawnerData = spawnerBlockEntity.saveWithFullMetadata(level.registryAccess());
			}
			else if (blockEntity instanceof TrialSpawnerBlockEntity trialSpawnerBlockEntity) {
				spawnerData = trialSpawnerBlockEntity.saveWithFullMetadata(level.registryAccess());
			}

			outStack = blockState.getCloneItemStack(level, blockPos, true);
			if (spawnerData != null) {
				CompoundTag compoundTag = new CompoundTag();
				compoundTag.put("spawnerData", spawnerData);
				outStack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));

				if (spawnerDisplayEntity != null) {
					outStack.set(DataComponents.CUSTOM_NAME, spawnerDisplayEntity.getName().copy().append(Component.literal(" ").append(block.getName())));
				}
			}
		}
		else if (ConfigHandler.enableFullCakeDrop && block instanceof CakeBlock) {
			if (blockState.getValue(CakeBlock.BITES) == 0) {
				outStack = new ItemStack(block);
			}
		}
		else if (ConfigHandler.enableFullCakeDrop && block instanceof CandleCakeBlock) {
			outStack = new ItemStack(Blocks.CAKE);

			CandleBlock candleBlock = ((CandleCakeBlockAccessor)block).getCandleBlock();
			if (candleBlock != null) {
				level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, new ItemStack(candleBlock)));
			}
		}
		else if ((ConfigHandler.enableBuddedAmethystDrop && block instanceof BuddingAmethystBlock && isPickaxe) ||
				(ConfigHandler.enableFarmlandDrop && block instanceof FarmlandBlock) ||
				(ConfigHandler.enableTallGrassDrop && block instanceof TallGrassBlock) ||
				(ConfigHandler.enableVinesDrop && block instanceof VineBlock) ||
				(ConfigHandler.enableSnowLayerDrop && block instanceof SnowLayerBlock) ||
				(ConfigHandler.enableInfestedStoneDrop && block instanceof InfestedBlock && isPickaxe)) {
			outStack = blockState.getCloneItemStack(level, blockPos, true);
		}

		if (outStack != null) {
			level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, outStack));
			level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
			ItemFunctions.itemHurtBreakAndEvent(handStack, (ServerPlayer)player, interactionHand, 1);
			return false;
		}

		return true;
	}

	public static boolean onEntityBlockPlace(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		if (level.isClientSide()) {
			return true;
		}

		if (!(livingEntity instanceof Player player)) {
			return true;
		}

		ItemStack handStack = player.getItemInHand(InteractionHand.MAIN_HAND);
		Item handItem = handStack.getItem();
		if (!Util.isSpawnerItem(handItem)) {
			handStack = player.getItemInHand(InteractionHand.OFF_HAND);
			handItem = handStack.getItem();
			if (!Util.isSpawnerItem(handItem)) {
				return true;
			}
		}

		CustomData customData = handStack.get(DataComponents.CUSTOM_DATA);
		if (customData == null) {
			return true;
		}

		CompoundTag compoundTag = customData.copyTag();
		if (!compoundTag.contains("spawnerData")) {
			return true;
		}

		CompoundTag spawnerData = (CompoundTag)compoundTag.get("spawnerData");
		if (spawnerData == null) {
			return true;
		}

		level.setBlock(blockPos, blockState, 3);
		TaskFunctions.enqueueCollectiveServerTask(level.getServer(), () -> {
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			if (blockEntity instanceof SpawnerBlockEntity spawnerBlockEntity) {
				spawnerBlockEntity.loadWithComponents(TagValueInput.create(new ProblemReporter.Collector(), level.registryAccess(), spawnerData));
			}
			else if (blockEntity instanceof TrialSpawnerBlockEntity trialSpawnerBlockEntity) {
				trialSpawnerBlockEntity.loadWithComponents(TagValueInput.create(new ProblemReporter.Collector(), level.registryAccess(), spawnerData));
			}
		}, 0);

		handStack.shrink(1);
		return false;
	}
}