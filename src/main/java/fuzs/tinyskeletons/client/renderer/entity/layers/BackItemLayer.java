package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.math.vector.Vector3f;

public class BackItemLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    public BackItemLayer(IEntityRenderer<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemstack = livingEntity.getOffhandItem();
        if (!itemstack.isEmpty()) {
            matrixStack.pushPose();
            ModelRenderer modelPart = this.getParentModel().body;
            modelPart.translateAndRotate(matrixStack);
            float scale = 2.0F;
            float backOffset = livingEntity.hasItemInSlot(EquipmentSlotType.CHEST) ? 0.24F : 0.18F;
            if (this.getParentModel().young) {
                matrixStack.translate(0.0D, 0.75D, 0.0);
                scale *= 0.5F;
                backOffset *= 0.5F;
            }
            if (!(itemstack.getItem() instanceof TridentItem)) {
                matrixStack.translate(0.0D, 0.0D, backOffset);
                matrixStack.scale(scale, scale, scale);
                if (itemstack.getItem() instanceof FishingRodItem || itemstack.getItem() instanceof OnAStickItem) {
                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                    matrixStack.translate(0.0D, -0.3D, 0.0D);
                }
                Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, vertexConsumerProvider, i);
            } else {
                scale *= 0.5F;
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(52.0F));
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(40.0F));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-25.F));
                matrixStack.scale(scale, -scale, -scale);
                matrixStack.translate(-backOffset, 0.0D, 0.0D);
                Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            }
            matrixStack.popPose();
        }
    }
}
