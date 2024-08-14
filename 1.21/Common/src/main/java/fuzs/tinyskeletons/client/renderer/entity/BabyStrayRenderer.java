package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.StrayClothingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class BabyStrayRenderer extends SkeletonRenderer {
   public static final ResourceLocation BABY_STRAY_SKELETON_LOCATION = TinySkeletons.id("textures/entity/skeleton/baby_stray.png");

   public BabyStrayRenderer(EntityRendererProvider.Context context) {
      super(context, ModClientRegistry.BABY_STRAY, ModClientRegistry.BABY_STRAY_INNER_ARMOR, ModClientRegistry.BABY_STRAY_OUTER_ARMOR);
      this.addLayer(new StrayClothingLayer<>(this, context.getModelSet()));
   }

   @Override
   public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
      return BABY_STRAY_SKELETON_LOCATION;
   }
}