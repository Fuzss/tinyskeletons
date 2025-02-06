package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.tinyskeletons.client.core.ClientAbstractions;
import fuzs.tinyskeletons.client.renderer.entity.state.BabyWitherSkeletonRenderState;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SkullInHandLayer<S extends BabyWitherSkeletonRenderState, M extends EntityModel<S> & ArmedModel> extends ItemInHandLayer<S, M> {

    public SkullInHandLayer(RenderLayerParent<S, M> renderLayerParent, ItemRenderer itemRenderer) {
        super(renderLayerParent, itemRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, S livingEntityRenderState, float yRot, float xRot) {
        ItemStack itemstack = livingEntityRenderState.skullItem;
        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            this.renderHandSkullItem(itemstack, poseStack, multiBufferSource, packedLight);
            poseStack.popPose();
        } else {
            super.render(poseStack, multiBufferSource, packedLight, livingEntityRenderState, yRot, xRot);
        }
    }

    private void renderHandSkullItem(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        // mostly copied from enderman held block layer renderer
        poseStack.pushPose();
        poseStack.translate(0.0F, -0.075F, 0.325F);
        poseStack.translate(0.0F, 0.6875F, -0.75F);
        poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(0.25F, 0.1875F, 0.25F);
        poseStack.scale(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        
        
//        poseStack.translate(0.0D, 0.6875D, -0.75D);
//        poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
//        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
//        poseStack.translate(0.25D, 0.1875D, 0.25D);
//        poseStack.scale(-0.5F, -0.5F, 0.5F);
//        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        ClientAbstractions.INSTANCE.renderByItem(itemStack,
                ItemDisplayContext.NONE,
                poseStack,
                multiBufferSource,
                packedLight,
                packedLight);
        poseStack.popPose();
    }
}