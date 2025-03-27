package com.natamus.silkiertouch.mixin;

import com.natamus.collective.functions.ConfigFunctions;
import com.natamus.silkiertouch.util.Reference;
import net.minecraft.world.level.block.TrialSpawnerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = TrialSpawnerBlock.class, priority = 1001)
public class TrialSpawnerBlockMixin {
    @ModifyVariable(method = "<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", at = @At(value = "HEAD"), argsOnly = true)
    private static BlockBehaviour.Properties TrialSpawnerBlock_properties(BlockBehaviour.Properties properties) {
        String rawEnableTrialSpawnerDrop = ConfigFunctions.getDictValues(Reference.MOD_ID).get("enableTrialSpawnerDrop");
        if (!"true".equals(rawEnableTrialSpawnerDrop)) {
            return properties;
        }

        return properties.requiresCorrectToolForDrops().strength(5F);
    }
}
