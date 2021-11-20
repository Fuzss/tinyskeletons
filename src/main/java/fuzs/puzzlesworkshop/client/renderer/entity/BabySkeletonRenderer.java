package fuzs.puzzlesworkshop.client.renderer.entity;

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
public class BabySkeletonRenderer extends MobRenderer<AbstractSkeletonEntity, SkeletonModel<AbstractSkeletonEntity>> {
   private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");

   public BabySkeletonRenderer(EntityRendererManager entityRendererManager) {
      super(entityRendererManager, new SkeletonModel<>(), 0.5F);
      this.addLayer(new HeadLayer<>(this));
      this.addLayer(new ElytraLayer<>(this));
      this.addLayer(new MainHandItemLayer<>(this));
      this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel(0.5F, true), new SkeletonModel(1.0F, true)));
      this.addLayer(new BackItemLayer<>(this));
   }

   @Override
   public ResourceLocation getTextureLocation(AbstractSkeletonEntity entity) {
      return SKELETON_LOCATION;
   }
}