package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.tinyskeletons.world.entity.monster.SkullCarryingMob;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.RenderProperties;

@OnlyIn(Dist.CLIENT)
public class HeldSkullItemLayer<T extends LivingEntity & SkullCarryingMob, M extends EntityModel<T> & ArmedModel> extends ItemInHandLayer<T, M> {
   public HeldSkullItemLayer(RenderLayerParent<T, M> p_i50934_1_) {
      super(p_i50934_1_);
   }

   @Override
   public void render(PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int combinedLight, T entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      ItemStack itemstack = entity.getSkullItem();
      if (!itemstack.isEmpty()) {
         matrixStack.pushPose();
         this.renderHandSkullItem(itemstack, matrixStack, renderTypeBuffer, combinedLight);
         matrixStack.popPose();
      } else {
         super.render(matrixStack, renderTypeBuffer, combinedLight, entity, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
      }
   }

   private void renderHandSkullItem(ItemStack itemStack, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int combinedLight) {
      // mostly copied from enderman held block layer renderer
      matrixStack.pushPose();
      matrixStack.translate(0.0D, 0.075, 0.375D);
      matrixStack.translate(0.0D, 0.6875D, -0.75D);
      matrixStack.mulPose(Vector3f.XP.rotationDegrees(20.0F));
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
      matrixStack.translate(0.25D, 0.1875D, 0.25D);
      matrixStack.scale(-0.5F, -0.5F, 0.5F);
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
      RenderProperties.get(itemStack).getItemStackRenderer().renderByItem(itemStack, ItemTransforms.TransformType.NONE, matrixStack, renderTypeBuffer, combinedLight, combinedLight);
      matrixStack.popPose();
   }
}