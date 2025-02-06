package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.model.BabyWitherSkeletonModel;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.client.renderer.entity.layers.SkullInHandLayer;
import fuzs.tinyskeletons.client.renderer.entity.state.BabyWitherSkeletonRenderState;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeleton;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class BabyWitherSkeletonRenderer extends AbstractSkeletonRenderer<BabyWitherSkeleton, BabyWitherSkeletonRenderState> {
    public static final ResourceLocation WITHER_SKELETON_LOCATION = TinySkeletons.id(BabySkeletonPackResources.WITHER_SKELETON_LOCATION.getPath());

    public BabyWitherSkeletonRenderer(EntityRendererProvider.Context context) {
        this(context,
                ModClientRegistry.BABY_WITHER_SKELETON,
                ModClientRegistry.BABY_WITHER_SKELETON_INNER_ARMOR,
                ModClientRegistry.BABY_WITHER_SKELETON_OUTER_ARMOR);
    }

    protected BabyWitherSkeletonRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ModelLayerLocation innerArmorModelLayerLocation, ModelLayerLocation outerArmorModelLayerLocation) {
        super(context,
                innerArmorModelLayerLocation,
                outerArmorModelLayerLocation,
                new BabyWitherSkeletonModel(context.bakeLayer(modelLayerLocation)));
        this.layers.removeIf(renderLayer -> renderLayer instanceof HumanoidArmorLayer ||
                renderLayer instanceof ItemInHandLayer);
        this.addLayer(new SkullInHandLayer<>(this, context.getItemRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new BabyWitherSkeletonModel(context.bakeLayer(innerArmorModelLayerLocation)),
                new BabyWitherSkeletonModel(context.bakeLayer(outerArmorModelLayerLocation)),
                context.getEquipmentRenderer()));
    }

    @Override
    public void extractRenderState(BabyWitherSkeleton abstractSkeleton, BabyWitherSkeletonRenderState skeletonRenderState, float partialTick) {
        super.extractRenderState(abstractSkeleton, skeletonRenderState, partialTick);
        skeletonRenderState.renderCarryingSkull = abstractSkeleton.renderCarryingSkull();
        skeletonRenderState.isDancing = abstractSkeleton.isDancing();
        skeletonRenderState.skullItem = abstractSkeleton.getSkullItem();
    }

    @Override
    public BabyWitherSkeletonRenderState createRenderState() {
        return new BabyWitherSkeletonRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(BabyWitherSkeletonRenderState livingEntityRenderState) {
        return WITHER_SKELETON_LOCATION;
    }
}