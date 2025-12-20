package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.client.model.geom.ModModelLayers;
import fuzs.tinyskeletons.client.packs.VanillaTexture;
import fuzs.tinyskeletons.world.entity.monster.BabyBogged;
import net.minecraft.client.model.monster.skeleton.BoggedModel;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.client.renderer.entity.state.BoggedRenderState;
import net.minecraft.resources.Identifier;

/**
 * @see net.minecraft.client.renderer.entity.BoggedRenderer
 */
public class BabyBoggedRenderer extends AbstractSkeletonRenderer<BabyBogged, BoggedRenderState> {
    public static final VanillaTexture BOGGED_SKELETON_TEXTURE = new VanillaTexture(
            "textures/entity/skeleton/bogged.png");
    public static final VanillaTexture BOGGED_OUTER_LAYER_TEXTURE = new VanillaTexture(
            "textures/entity/skeleton/bogged_overlay.png");

    public BabyBoggedRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModModelLayers.BABY_BOGGED_ARMOR,
                new BoggedModel(context.bakeLayer(ModModelLayers.BABY_BOGGED)));
        this.addLayer(new SkeletonClothingLayer<>(this,
                context.getModelSet(),
                ModModelLayers.BABY_BOGGED_OUTER_LAYER,
                BOGGED_OUTER_LAYER_TEXTURE.id()));
    }

    @Override
    public BoggedRenderState createRenderState() {
        return new BoggedRenderState();
    }

    @Override
    public void extractRenderState(BabyBogged babyBogged, BoggedRenderState boggedRenderState, float partialTick) {
        super.extractRenderState(babyBogged, boggedRenderState, partialTick);
        boggedRenderState.isSheared = babyBogged.isSheared();
    }

    @Override
    public Identifier getTextureLocation(BoggedRenderState livingEntityRenderState) {
        return BOGGED_SKELETON_TEXTURE.id();
    }
}
