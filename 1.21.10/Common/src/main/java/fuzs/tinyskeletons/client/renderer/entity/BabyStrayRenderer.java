package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModModelLayers;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;

public class BabyStrayRenderer extends AbstractSkeletonRenderer<BabyStray, SkeletonRenderState> {
    public static final ResourceLocation STRAY_SKELETON_LOCATION = TinySkeletons.id(BabySkeletonPackResources.STRAY_SKELETON_LOCATION.getPath());
    public static final ResourceLocation STRAY_CLOTHES_LOCATION = TinySkeletons.id(BabySkeletonPackResources.STRAY_CLOTHES_LOCATION.getPath());

    public BabyStrayRenderer(EntityRendererProvider.Context context) {
        super(context, ModModelLayers.BABY_STRAY, ModModelLayers.BABY_STRAY_ARMOR);
        this.addLayer(new SkeletonClothingLayer<>(this,
                context.getModelSet(),
                ModModelLayers.BABY_STRAY_OUTER_LAYER,
                STRAY_CLOTHES_LOCATION));
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonRenderState livingEntityRenderState) {
        return STRAY_SKELETON_LOCATION;
    }
}