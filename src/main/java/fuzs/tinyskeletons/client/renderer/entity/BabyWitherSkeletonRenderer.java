package fuzs.tinyskeletons.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.client.model.SkullCarryingSkeletonModel;
import fuzs.tinyskeletons.client.registry.ModClientRegistry;
import fuzs.tinyskeletons.client.renderer.entity.layers.HeldSkullItemLayer;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeletonEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyWitherSkeletonRenderer extends MobRenderer<BabyWitherSkeletonEntity, SkullCarryingSkeletonModel<BabyWitherSkeletonEntity>> {
   private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public BabyWitherSkeletonRenderer(EntityRendererProvider.Context entityRendererManager) {
      this(entityRendererManager, ModClientRegistry.BABY_WITHER_SKELETON, ModClientRegistry.BABY_WITHER_SKELETON_INNER_ARMOR, ModClientRegistry.BABY_WITHER_SKELETON_OUTER_ARMOR);
   }

   public BabyWitherSkeletonRenderer(EntityRendererProvider.Context entityRendererManager, ModelLayerLocation entityLocation, ModelLayerLocation innerArmorLocation, ModelLayerLocation outerArmorLocation) {
      super(entityRendererManager, new SkullCarryingSkeletonModel<>(entityRendererManager.bakeLayer(entityLocation)), 0.5F);
      this.addLayer(new CustomHeadLayer<>(this, entityRendererManager.getModelSet()));
      this.addLayer(new ElytraLayer<>(this, entityRendererManager.getModelSet()));
      this.addLayer(new HeldSkullItemLayer<>(this));
      this.addLayer(new HumanoidArmorLayer<>(this, new SkullCarryingSkeletonModel<>(entityRendererManager.bakeLayer(innerArmorLocation)), new SkullCarryingSkeletonModel<>(entityRendererManager.bakeLayer(outerArmorLocation))));
   }

   @Override
   public ResourceLocation getTextureLocation(BabyWitherSkeletonEntity p_110775_1_) {
      return WITHER_SKELETON_LOCATION;
   }

   @Override
   protected void scale(BabyWitherSkeletonEntity p_225620_1_, PoseStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.scale(1.2F, 1.2F, 1.2F);
   }
}