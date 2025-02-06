package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.tinyskeletons.client.renderer.entity.state.BabyWitherSkeletonRenderState;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class SkullInHandLayer<S extends BabyWitherSkeletonRenderState, M extends EntityModel<S> & ArmedModel> extends ItemInHandLayer<S, M> {
    private final BlockRenderDispatcher blockRenderer;

    public SkullInHandLayer(RenderLayerParent<S, M> renderLayerParent, BlockRenderDispatcher blockRenderer) {
        super(renderLayerParent);
        this.blockRenderer = blockRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        ItemStack itemStack = renderState.skullItem;
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            this.renderHandSkullItem(blockItem.getBlock().defaultBlockState(), poseStack, bufferSource, packedLight);
        } else {
            super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
        }
    }

    /**
     * Mostly copied from {@link net.minecraft.client.renderer.entity.layers.CarriedBlockLayer}.
     */
    private void renderHandSkullItem(BlockState blockState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, -0.075F, 0.325F);
        poseStack.translate(0.0F, 0.6875F, -0.75F);
        poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(0.25F, 0.1875F, 0.25F);
        poseStack.scale(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        this.blockRenderer.renderSingleBlock(blockState,
                poseStack,
                bufferSource,
                packedLight,
                OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}