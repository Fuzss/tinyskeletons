package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.client.registry.ModClientRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.StrayClothingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyStrayRenderer extends SkeletonRenderer {
   private static final ResourceLocation STRAY_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/stray.png");

   public BabyStrayRenderer(EntityRendererProvider.Context p_174409_) {
      super(p_174409_, ModClientRegistry.BABY_STRAY, ModClientRegistry.BABY_STRAY_INNER_ARMOR, ModClientRegistry.BABY_STRAY_OUTER_ARMOR);
      this.addLayer(new StrayClothingLayer<>(this, p_174409_.getModelSet()));
   }

   @Override
   public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
      return STRAY_SKELETON_LOCATION;
   }
}