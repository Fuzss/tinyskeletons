package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.resources.ResourceLocation;

public class BabyStrayRenderer extends SkeletonRenderer<BabyStray> {
   public static final ResourceLocation BABY_STRAY_SKELETON_LOCATION = TinySkeletons.id("textures/entity/skeleton/baby_stray.png");
   private static final ResourceLocation STRAY_CLOTHES_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/skeleton/stray_overlay.png");

   public BabyStrayRenderer(EntityRendererProvider.Context context) {
      super(context, ModClientRegistry.BABY_STRAY, ModClientRegistry.BABY_STRAY_INNER_ARMOR, ModClientRegistry.BABY_STRAY_OUTER_ARMOR);
      this.addLayer(new SkeletonClothingLayer<>(this, context.getModelSet(), ModClientRegistry.BABY_STRAY_OUTER_LAYER, STRAY_CLOTHES_LOCATION));
   }

   @Override
   public ResourceLocation getTextureLocation(BabyStray entity) {
      return BABY_STRAY_SKELETON_LOCATION;
   }
}