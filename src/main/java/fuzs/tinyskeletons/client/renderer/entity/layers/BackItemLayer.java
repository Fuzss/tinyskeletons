package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;

public class BackItemLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public BackItemLayer(RenderLayerParent<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemstack = livingEntity.getOffhandItem();
        if (!itemstack.isEmpty()) {
            matrixStack.pushPose();
            ModelPart modelPart = this.getParentModel().body;
            modelPart.translateAndRotate(matrixStack);
            float scale = 2.0F;
            float backOffset = livingEntity.hasItemInSlot(EquipmentSlot.CHEST) ? 0.24F : 0.18F;
            if (this.getParentModel().young) {
                matrixStack.translate(0.0D, 0.75D, 0.0);
                scale *= 0.5F;
                backOffset *= 0.5F;
            }
            if (!(itemstack.getItem() instanceof TridentItem)) {
                matrixStack.translate(0.0D, 0.0D, backOffset);
                matrixStack.scale(scale, scale, scale);
                if (itemstack.getItem() instanceof FishingRodItem || itemstack.getItem() instanceof FoodOnAStickItem) {
                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                    matrixStack.translate(0.0D, -0.3D, 0.0D);
                }
                Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemstack, ItemTransforms.TransformType.GROUND, false, matrixStack, vertexConsumerProvider, i);
            } else {
                scale *= 0.5F;
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(52.0F));
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(40.0F));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-25.F));
                matrixStack.scale(scale, -scale, -scale);
                matrixStack.translate(-backOffset, 0.0D, 0.0D);
                Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemstack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            }
            matrixStack.popPose();
        }
    }
}
