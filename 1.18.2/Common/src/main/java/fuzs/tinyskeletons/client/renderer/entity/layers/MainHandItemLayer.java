package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MainHandItemLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public MainHandItemLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, T livingEntity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ItemStack itemstack = livingEntity.getMainHandItem();
        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            if (this.getParentModel().young) {
                poseStack.translate(0.0D, 0.75D, 0.0D);
                poseStack.scale(0.5F, 0.5F, 0.5F);
            }
            if (livingEntity.getMainArm() == HumanoidArm.RIGHT) {
                this.renderArmWithItem(livingEntity, itemstack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, multiBufferSource, packedLight);
            } else {
                this.renderArmWithItem(livingEntity, itemstack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, multiBufferSource, packedLight);
            }
            poseStack.popPose();
        }
    }

    private void renderArmWithItem(LivingEntity livingEntity, ItemStack stack, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            this.getParentModel().translateToHand(humanoidArm, poseStack);
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            boolean leftArm = humanoidArm == HumanoidArm.LEFT;
            poseStack.translate((double) ((float) (leftArm ? -1 : 1) / 16.0F), 0.125D, -0.625D);
            this.itemInHandRenderer.renderItem(livingEntity, stack, transformType, leftArm, poseStack, multiBufferSource, packedLight);
            poseStack.popPose();
        }
    }
}