package fuzs.puzzlesworkshop.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import fuzs.puzzlesworkshop.client.renderer.entity.model.BabyStrayModel;
import fuzs.puzzlesworkshop.entity.monster.BabyStrayEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyStrayClothingLayer extends LayerRenderer<BabyStrayEntity, BabyStrayModel> {
   private static final ResourceLocation STRAY_CLOTHES_LOCATION = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
   private final BabyStrayModel layerModel = new BabyStrayModel(0.25F, true);

   public BabyStrayClothingLayer(IEntityRenderer<BabyStrayEntity, BabyStrayModel> p_i50919_1_) {
      super(p_i50919_1_);
   }

   public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, BabyStrayEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      coloredCutoutModelCopyLayerRender(this.getParentModel(), this.layerModel, STRAY_CLOTHES_LOCATION, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, 1.0F, 1.0F, 1.0F);
   }
}