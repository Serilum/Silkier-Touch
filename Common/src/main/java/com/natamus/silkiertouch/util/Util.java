package com.natamus.silkiertouch.util;

import com.natamus.silkiertouch.config.ConfigHandler;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

public class Util {
    public static boolean hasSilkTouch(Level level, ItemStack itemStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), itemStack) >= 1;
    }

    public static boolean isSpawnerItem(Item item) {
        return item.equals(Items.SPAWNER) || item.equals(Items.TRIAL_SPAWNER);
    }
    public static boolean isSpawnerBlock(Block block) {
        return block instanceof SpawnerBlock || block instanceof TrialSpawnerBlock;
    }

    public static boolean isSilkTouchDrop(Block block, boolean isPickaxe) {
        return (ConfigHandler.enableBuddedAmethystDrop && block instanceof BuddingAmethystBlock && isPickaxe) ||
                (ConfigHandler.enableFarmlandDrop && block instanceof FarmBlock) ||
                (ConfigHandler.enableTallGrassDrop && (block instanceof TallGrassBlock || block instanceof DoublePlantBlock)) ||
                (ConfigHandler.enableVinesDrop && block instanceof VineBlock) ||
                (ConfigHandler.enableSnowLayerDrop && block instanceof SnowLayerBlock) ||
                (ConfigHandler.enableInfestedStoneDrop && block instanceof InfestedBlock && isPickaxe);
    }
}
