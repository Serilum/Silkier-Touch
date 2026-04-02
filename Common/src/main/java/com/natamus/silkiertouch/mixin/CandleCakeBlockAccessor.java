package com.natamus.silkiertouch.mixin;

import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CandleCakeBlock.class, priority = 1001)
public interface CandleCakeBlockAccessor {
	@Accessor CandleBlock getCandleBlock();
}
