package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
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
   public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      ItemStack itemstack = p_225628_4_.getMainHandItem();
      if (!itemstack.isEmpty()) {
         p_225628_1_.pushPose();
         if (this.getParentModel().young) {
            p_225628_1_.translate(0.0D, 0.75D, 0.0D);
            p_225628_1_.scale(0.5F, 0.5F, 0.5F);
         }
         if (p_225628_4_.getMainArm() == HumanoidArm.RIGHT) {
            this.renderArmWithItem(p_225628_4_, itemstack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, p_225628_1_, p_225628_2_, p_225628_3_);
         } else {
            this.renderArmWithItem(p_225628_4_, itemstack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, p_225628_1_, p_225628_2_, p_225628_3_);
         }
         p_225628_1_.popPose();
      }
   }

   private void renderArmWithItem(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemTransforms.TransformType p_229135_3_, HumanoidArm p_229135_4_, PoseStack p_229135_5_, MultiBufferSource p_229135_6_, int p_229135_7_) {
      if (!p_229135_2_.isEmpty()) {
         p_229135_5_.pushPose();
         this.getParentModel().translateToHand(p_229135_4_, p_229135_5_);
         p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
         p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         boolean leftArm = p_229135_4_ == HumanoidArm.LEFT;
         p_229135_5_.translate((double)((float)(leftArm ? -1 : 1) / 16.0F), 0.125D, -0.625D);
         this.itemInHandRenderer.renderItem(p_229135_1_, p_229135_2_, p_229135_3_, leftArm, p_229135_5_, p_229135_6_, p_229135_7_);
         p_229135_5_.popPose();
      }
   }
}