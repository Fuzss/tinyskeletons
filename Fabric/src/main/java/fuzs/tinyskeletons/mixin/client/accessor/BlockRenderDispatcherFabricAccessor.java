package fuzs.tinyskeletons.mixin.client.accessor;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockRenderDispatcher.class)
public interface BlockRenderDispatcherFabricAccessor {

    @Accessor("blockEntityRenderer")
    BlockEntityWithoutLevelRenderer tinyskeletons$getBlockEntityRenderer();
}
