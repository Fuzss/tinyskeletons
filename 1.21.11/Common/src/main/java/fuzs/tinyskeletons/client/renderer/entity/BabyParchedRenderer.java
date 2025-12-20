package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.client.model.geom.ModModelLayers;
import fuzs.tinyskeletons.client.packs.VanillaTexture;
import fuzs.tinyskeletons.world.entity.monster.BabyParched;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

/**
 * @see net.minecraft.client.renderer.entity.ParchedRenderer
 */
public class BabyParchedRenderer extends AbstractSkeletonRenderer<BabyParched, SkeletonRenderState> {
    public static final VanillaTexture PARCHED_SKELETON_TEXTURE = new VanillaTexture(
            "textures/entity/skeleton/parched.png");

    public BabyParchedRenderer(EntityRendererProvider.Context context) {
        super(context, ModModelLayers.BABY_PARCHED, ModModelLayers.BABY_PARCHED_ARMOR);
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }

    @Override
    public Identifier getTextureLocation(SkeletonRenderState livingEntityRenderState) {
        return PARCHED_SKELETON_TEXTURE.id();
    }
}
