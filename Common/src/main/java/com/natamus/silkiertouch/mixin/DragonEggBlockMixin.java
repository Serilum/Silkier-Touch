package com.natamus.silkiertouch.mixin;

import com.natamus.collective.services.Services;
import com.natamus.silkiertouch.config.ConfigHandler;
import com.natamus.silkiertouch.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DragonEggBlock.class, priority = 1001)
public class DragonEggBlockMixin {
    @Inject(method = "attack(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V", at = @At(value = "HEAD"), cancellable = true)
    protected void attack(BlockState blockState, Level level, BlockPos blockPos, Player player, CallbackInfo ci) {
        if (!ConfigHandler.enableDragonEggDrop) {
            return;
        }

        ItemStack handStack = player.getMainHandItem();
        if (!Services.TOOLFUNCTIONS.isPickaxe(handStack)) {
            return;
        }

		if (!Util.hasSilkTouch(level, handStack)) {
			return;
		}

        ci.cancel();
    }
}
