package fuzs.puzzlesworkshop.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MainHandItemLayer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M> {
   public MainHandItemLayer(IEntityRenderer<T, M> p_i50934_1_) {
      super(p_i50934_1_);
   }

   public void render(MatrixStack p_229135_5_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      ItemStack itemstack = p_225628_4_.getMainHandItem();
      if (!itemstack.isEmpty()) {
         p_229135_5_.pushPose();
         if (p_225628_4_.getMainArm() == HandSide.RIGHT) {
            this.renderArmWithItem(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, p_229135_5_, p_225628_2_, p_225628_3_);
         } else {
            this.renderArmWithItem(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, p_229135_5_, p_225628_2_, p_225628_3_);
         }
         p_229135_5_.popPose();
      }
   }

   private void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLight) {
      if (!itemStack.isEmpty()) {
         if (itemStack.getItem() instanceof BlockItem) {
            BlockItem blockItem = (BlockItem) itemStack.getItem();
            if (blockItem.getBlock() instanceof AbstractSkullBlock) {
               this.renderHandSkullItem(itemStack, matrixStack, renderTypeBuffer, combinedLight);
               return;
            }
         }
         this.renderHandItem(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, combinedLight);
      }
   }

   private void renderHandSkullItem(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLight) {
      // mostly copied from enderman held block layer renderer
      matrixStack.pushPose();
      matrixStack.translate(0.0D, 0.075, 0.375D);
      matrixStack.translate(0.0D, 0.6875D, -0.75D);
      matrixStack.mulPose(Vector3f.XP.rotationDegrees(20.0F));
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
      matrixStack.translate(0.25D, 0.1875D, 0.25D);
      matrixStack.scale(-0.5F, -0.5F, 0.5F);
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
      itemStack.getItem().getItemStackTileEntityRenderer().renderByItem(itemStack, ItemCameraTransforms.TransformType.NONE, matrixStack, renderTypeBuffer, combinedLight, combinedLight);
      matrixStack.popPose();
   }

   private void renderHandItem(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLight) {
      // moved here from main render method as it shouldn't affect skull rendering
      if (this.getParentModel().young) {
         matrixStack.translate(0.0D, 0.75D, 0.0D);
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      }
      matrixStack.pushPose();
      this.getParentModel().translateToHand(handSide, matrixStack);
      matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
      boolean flag = handSide == HandSide.LEFT;
      matrixStack.translate((double)((float)(flag ? -1 : 1) / 16.0F), 0.125D, -0.625D);
      Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemStack, transformType, flag, matrixStack, renderTypeBuffer, combinedLight);
      matrixStack.popPose();
   }
}