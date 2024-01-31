package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.mixin.client.accessor.BlockRenderDispatcherFabricAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay) {
        ((BlockRenderDispatcherFabricAccessor) Minecraft.getInstance().getBlockRenderer()).tinyskeletons$getBlockEntityRenderer().renderByItem(itemStack, ItemTransforms.TransformType.NONE, poseStack, multiBufferSource, lightmap, overlay);
    }
}
