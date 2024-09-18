package fuzs.tinyskeletons.fabric.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.client.core.ClientAbstractions;
import fuzs.tinyskeletons.fabric.mixin.client.accessor.BlockRenderDispatcherFabricAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ((BlockRenderDispatcherFabricAccessor) Minecraft.getInstance().getBlockRenderer()).tinyskeletons$getBlockEntityRenderer().renderByItem(itemStack, ItemDisplayContext.NONE, poseStack, multiBufferSource,
                packedLight,
                packedOverlay
        );
    }
}
