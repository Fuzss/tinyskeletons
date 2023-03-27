package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.renderer.entity.layers.BackItemLayer;
import fuzs.tinyskeletons.client.renderer.entity.layers.MainHandItemLayer;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class BabySkeletonRenderer extends MobRenderer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>> {
   public static final ResourceLocation BABY_SKELETON_LOCATION = TinySkeletons.id("textures/entity/skeleton/baby_skeleton.png");

   public BabySkeletonRenderer(EntityRendererProvider.Context entityRendererManager) {
      this(entityRendererManager, ModClientRegistry.BABY_SKELETON, ModClientRegistry.BABY_SKELETON_INNER_ARMOR, ModClientRegistry.BABY_SKELETON_OUTER_ARMOR);
   }

   public BabySkeletonRenderer(EntityRendererProvider.Context entityRendererManager, ModelLayerLocation entityLocation, ModelLayerLocation innerArmorLocation, ModelLayerLocation outerArmorLocation) {
      super(entityRendererManager, new SkeletonModel<>(entityRendererManager.bakeLayer(entityLocation)), 0.5F);
      this.addLayer(new CustomHeadLayer<>(this, entityRendererManager.getModelSet(), entityRendererManager.getItemInHandRenderer()));
      this.addLayer(new ElytraLayer<>(this, entityRendererManager.getModelSet()));
      this.addLayer(new MainHandItemLayer<>(this, entityRendererManager.getItemInHandRenderer()));
      this.addLayer(new BackItemLayer<>(this, entityRendererManager.getItemInHandRenderer()));
      this.addLayer(new HumanoidArmorLayer<>(this, new SkeletonModel<>(entityRendererManager.bakeLayer(innerArmorLocation)), new SkeletonModel<>(entityRendererManager.bakeLayer(outerArmorLocation))));
   }

   @Override
   public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
      return BABY_SKELETON_LOCATION;
   }

   @Override
   protected boolean isShaking(AbstractSkeleton abstractSkeleton) {
      return abstractSkeleton.isShaking();
   }
}