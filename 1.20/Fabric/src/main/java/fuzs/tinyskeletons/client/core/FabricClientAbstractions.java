package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.mixin.client.accessor.BlockRenderDispatcherFabricAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay) {
        ((BlockRenderDispatcherFabricAccessor) Minecraft.getInstance().getBlockRenderer()).tinyskeletons$getBlockEntityRenderer().renderByItem(itemStack, ItemDisplayContext.NONE, poseStack, multiBufferSource, lightmap, overlay);
    }
}
