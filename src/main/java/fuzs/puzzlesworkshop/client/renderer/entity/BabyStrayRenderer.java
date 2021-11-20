package fuzs.puzzlesworkshop.client.renderer.entity;

import fuzs.puzzlesworkshop.client.renderer.entity.layers.BabyStrayClothingLayer;
import fuzs.puzzlesworkshop.client.renderer.entity.model.BabyStrayModel;
import fuzs.puzzlesworkshop.entity.monster.BabyStrayEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyStrayRenderer extends BipedRenderer<BabyStrayEntity, BabyStrayModel> {
   private static final ResourceLocation STRAY_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/stray.png");

   public BabyStrayRenderer(EntityRendererManager p_i46143_1_) {
      super(p_i46143_1_, new BabyStrayModel(), 0.5F);
      this.addLayer(new BipedArmorLayer<>(this, new BabyStrayModel(0.5F, true), new BabyStrayModel(1.0F, true)));
      // since the model is slightly different we need a custom clothing layer
      this.addLayer(new BabyStrayClothingLayer(this));
   }

   @Override
   public ResourceLocation getTextureLocation(BabyStrayEntity p_110775_1_) {
      return STRAY_SKELETON_LOCATION;
   }
}