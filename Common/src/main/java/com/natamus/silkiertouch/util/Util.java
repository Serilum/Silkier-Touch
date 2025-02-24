package com.natamus.silkiertouch.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;

public class Util {
    public static boolean hasSilkTouch(Level level, ItemStack itemStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) >= 1;
    }

    public static boolean isSpawnerItem(Item item) {
        return item.equals(Items.SPAWNER);
    }
    public static boolean isSpawnerBlock(Block block) {
        return block instanceof SpawnerBlock;
    }
}
