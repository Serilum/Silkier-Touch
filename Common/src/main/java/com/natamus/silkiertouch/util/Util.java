package com.natamus.silkiertouch.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.TrialSpawnerBlock;

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
}
