package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.client.model.geom.ModModelLayers;
import fuzs.tinyskeletons.client.packs.VanillaTexture;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

/**
 * @see net.minecraft.client.renderer.entity.StrayRenderer
 */
public class BabyStrayRenderer extends AbstractSkeletonRenderer<BabyStray, SkeletonRenderState> {
    public static final VanillaTexture STRAY_SKELETON_TEXTURE = new VanillaTexture("textures/entity/skeleton/stray.png");
    public static final VanillaTexture STRAY_CLOTHES_TEXTURE = new VanillaTexture(
            "textures/entity/skeleton/stray_overlay.png");

    public BabyStrayRenderer(EntityRendererProvider.Context context) {
        super(context, ModModelLayers.BABY_STRAY, ModModelLayers.BABY_STRAY_ARMOR);
        this.addLayer(new SkeletonClothingLayer<>(this,
                context.getModelSet(),
                ModModelLayers.BABY_STRAY_OUTER_LAYER,
                STRAY_CLOTHES_TEXTURE.id()));
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }

    @Override
    public Identifier getTextureLocation(SkeletonRenderState livingEntityRenderState) {
        return STRAY_SKELETON_TEXTURE.id();
    }
}
