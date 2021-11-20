package fuzs.puzzlesworkshop.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import fuzs.puzzlesworkshop.client.renderer.entity.layers.BackItemLayer;
import fuzs.puzzlesworkshop.client.renderer.entity.layers.MainHandItemLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyWitherSkeletonRenderer extends MobRenderer<AbstractSkeletonEntity, SkeletonModel<AbstractSkeletonEntity>> {
   private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public BabyWitherSkeletonRenderer(EntityRendererManager entityRendererManager) {
      super(entityRendererManager, new SkeletonModel<>(), 0.5F);
      this.addLayer(new HeadLayer<>(this));
      this.addLayer(new ElytraLayer<>(this));
      this.addLayer(new MainHandItemLayer<>(this));
      this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
   }

   @Override
   public ResourceLocation getTextureLocation(AbstractSkeletonEntity p_110775_1_) {
      return WITHER_SKELETON_LOCATION;
   }

   @Override
   protected void scale(AbstractSkeletonEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.scale(1.2F, 1.2F, 1.2F);
   }
}