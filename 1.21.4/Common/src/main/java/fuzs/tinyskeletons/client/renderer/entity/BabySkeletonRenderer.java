package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.client.renderer.entity.layers.ItemInMainHandLayer;
import fuzs.tinyskeletons.client.renderer.entity.layers.ItemOnBackLayer;
import fuzs.tinyskeletons.world.entity.monster.BabySkeleton;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;

public class BabySkeletonRenderer extends AbstractSkeletonRenderer<BabySkeleton, SkeletonRenderState> {
    public static final ResourceLocation SKELETON_LOCATION = TinySkeletons.id(BabySkeletonPackResources.SKELETON_LOCATION.getPath());

    public BabySkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModClientRegistry.BABY_SKELETON,
                ModClientRegistry.BABY_SKELETON_INNER_ARMOR,
                ModClientRegistry.BABY_SKELETON_OUTER_ARMOR);
        this.layers.removeIf(renderLayer -> renderLayer instanceof ItemInHandLayer);
        this.addLayer(new ItemInMainHandLayer<>(this, context.getItemRenderer()));
        this.addLayer(new ItemOnBackLayer<>(this, context.getItemRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonRenderState livingEntityRenderState) {
        return SKELETON_LOCATION;
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }
}