package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.client.registry.ModClientRegistry;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabySkeletonRenderer extends MobRenderer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>> {
   private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");

   public BabySkeletonRenderer(EntityRendererProvider.Context entityRendererManager) {
      this(entityRendererManager, ModClientRegistry.BABY_SKELETON, ModClientRegistry.BABY_SKELETON_INNER_ARMOR, ModClientRegistry.BABY_SKELETON_OUTER_ARMOR);
   }

   public BabySkeletonRenderer(EntityRendererProvider.Context entityRendererManager, ModelLayerLocation entityLocation, ModelLayerLocation innerArmorLocation, ModelLayerLocation outerArmorLocation) {
      super(entityRendererManager, new SkeletonModel<>(entityRendererManager.bakeLayer(entityLocation)), 0.5F);
      this.addLayer(new CustomHeadLayer<>(this, entityRendererManager.getModelSet()));
      this.addLayer(new ElytraLayer<>(this, entityRendererManager.getModelSet()));
      this.addLayer(new MainHandItemLayer<>(this));
      this.addLayer(new BackItemLayer<>(this));
      this.addLayer(new HumanoidArmorLayer<>(this, new SkeletonModel<>(entityRendererManager.bakeLayer(innerArmorLocation)), new SkeletonModel<>(entityRendererManager.bakeLayer(outerArmorLocation))));
   }

   @Override
   public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
      return SKELETON_LOCATION;
   }
}