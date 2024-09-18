package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.world.entity.monster.BabyBogged;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.resources.ResourceLocation;

public class BabyBoggedRenderer extends SkeletonRenderer<BabyBogged> {
    public static final ResourceLocation BABY_BOGGED_SKELETON_LOCATION = TinySkeletons.id(
            "textures/entity/skeleton/baby_bogged.png");
    private static final ResourceLocation BOGGED_OUTER_LAYER_LOCATION = ResourceLocation.withDefaultNamespace(
            "textures/entity/skeleton/bogged_overlay.png");

    public BabyBoggedRenderer(EntityRendererProvider.Context context) {
        super(context, ModClientRegistry.BABY_BOGGED, ModClientRegistry.BABY_BOGGED_INNER_ARMOR,
                ModClientRegistry.BABY_BOGGED_OUTER_ARMOR
        );
        this.addLayer(
                new SkeletonClothingLayer<>(this, context.getModelSet(), ModClientRegistry.BABY_BOGGED_OUTER_LAYER,
                        BOGGED_OUTER_LAYER_LOCATION
                ));
    }

    @Override
    public ResourceLocation getTextureLocation(BabyBogged entity) {
        return BABY_BOGGED_SKELETON_LOCATION;
    }
}