package fuzs.puzzlesworkshop.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import fuzs.puzzlesworkshop.client.renderer.entity.layers.HeldSkullItemLayer;
import fuzs.puzzlesworkshop.client.renderer.entity.layers.MainHandItemLayer;
import fuzs.puzzlesworkshop.client.renderer.entity.model.SkullCarryingSkeletonModel;
import fuzs.puzzlesworkshop.entity.monster.BabyWitherSkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyWitherSkeletonRenderer extends MobRenderer<BabyWitherSkeletonEntity, SkullCarryingSkeletonModel<BabyWitherSkeletonEntity>> {
   private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public BabyWitherSkeletonRenderer(EntityRendererManager entityRendererManager) {
      super(entityRendererManager, new SkullCarryingSkeletonModel<>(), 0.5F);
      this.addLayer(new HeadLayer<>(this));
      this.addLayer(new ElytraLayer<>(this));
      this.addLayer(new HeldSkullItemLayer<>(this));
      this.addLayer(new BipedArmorLayer<>(this, new SkullCarryingSkeletonModel<>(0.5F, true), new SkullCarryingSkeletonModel<>(1.0F, true)));
   }

   @Override
   public ResourceLocation getTextureLocation(BabyWitherSkeletonEntity p_110775_1_) {
      return WITHER_SKELETON_LOCATION;
   }

   @Override
   protected void scale(BabyWitherSkeletonEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.scale(1.2F, 1.2F, 1.2F);
   }
}