package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.world.entity.monster.BabyBogged;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.client.renderer.entity.state.BoggedRenderState;
import net.minecraft.resources.ResourceLocation;

public class BabyBoggedRenderer extends AbstractSkeletonRenderer<BabyBogged, BoggedRenderState> {
    public static final ResourceLocation BOGGED_SKELETON_LOCATION = TinySkeletons.id(BabySkeletonPackResources.BOGGED_SKELETON_LOCATION.getPath());
    public static final ResourceLocation BOGGED_OUTER_LAYER_LOCATION = TinySkeletons.id(BabySkeletonPackResources.BOGGED_OUTER_LAYER_LOCATION.getPath());

    public BabyBoggedRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModClientRegistry.BABY_BOGGED,
                ModClientRegistry.BABY_BOGGED_INNER_ARMOR,
                ModClientRegistry.BABY_BOGGED_OUTER_ARMOR);
        this.addLayer(new SkeletonClothingLayer<>(this,
                context.getModelSet(),
                ModClientRegistry.BABY_BOGGED_OUTER_LAYER,
                BOGGED_OUTER_LAYER_LOCATION));
    }

    @Override
    public BoggedRenderState createRenderState() {
        return new BoggedRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(BoggedRenderState livingEntityRenderState) {
        return BOGGED_SKELETON_LOCATION;
    }
}