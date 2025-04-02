package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.tinyskeletons.client.renderer.entity.state.BabySkeletonRenderState;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ItemOnBackLayer<M extends HumanoidModel<BabySkeletonRenderState> & ArmedModel> extends RenderLayer<BabySkeletonRenderState, M> {

    public ItemOnBackLayer(RenderLayerParent<BabySkeletonRenderState, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, BabySkeletonRenderState renderState, float yRot, float xRot) {
        ItemStackRenderState itemStackRenderState = BabySkeletonRenderState.getOffHandItem(renderState);
        if (!itemStackRenderState.isEmpty()) {
            poseStack.pushPose();
            ModelPart modelPart = this.getParentModel().body;
            modelPart.translateAndRotate(poseStack);
            float scale = 2.0F;
            float backOffset = !renderState.chestEquipment.isEmpty() ? 0.24F : 0.18F;
            if (renderState.offhandItemType != BabySkeletonRenderState.ItemType.TRIDENT) {
                poseStack.translate(0.0, 0.0, backOffset);
                poseStack.scale(scale, scale, scale);
                if (renderState.offhandItemType == BabySkeletonRenderState.ItemType.ROD) {
                    poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                    poseStack.translate(0.0, -0.3, 0.0);
                }
            } else {
                scale *= 0.5F;
                poseStack.mulPose(Axis.YP.rotationDegrees(52.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(40.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-25.0F));
                poseStack.scale(scale, -scale, -scale);
                poseStack.translate(-backOffset, 0.0, 0.0);
            }
            itemStackRenderState.render(poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
