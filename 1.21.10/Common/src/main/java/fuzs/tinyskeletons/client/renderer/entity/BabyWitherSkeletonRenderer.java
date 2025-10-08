package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModModelLayers;
import fuzs.tinyskeletons.client.model.BabyWitherSkeletonModel;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.client.renderer.entity.layers.SkullInHandLayer;
import fuzs.tinyskeletons.client.renderer.entity.state.BabyWitherSkeletonRenderState;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeleton;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class BabyWitherSkeletonRenderer extends AbstractSkeletonRenderer<BabyWitherSkeleton, BabyWitherSkeletonRenderState> {
    public static final ResourceLocation WITHER_SKELETON_LOCATION = TinySkeletons.id(BabySkeletonPackResources.WITHER_SKELETON_LOCATION.getPath());

    public BabyWitherSkeletonRenderer(EntityRendererProvider.Context context) {
        this(context, ModModelLayers.BABY_WITHER_SKELETON, ModModelLayers.BABY_WITHER_SKELETON_ARMOR);
    }

    protected BabyWitherSkeletonRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ArmorModelSet<ModelLayerLocation> armorModelSet) {
        super(context, armorModelSet, new BabyWitherSkeletonModel(context.bakeLayer(modelLayerLocation)));
        this.layers.removeIf((RenderLayer<BabyWitherSkeletonRenderState, SkeletonModel<BabyWitherSkeletonRenderState>> renderLayer) -> {
            return renderLayer instanceof HumanoidArmorLayer || renderLayer instanceof ItemInHandLayer;
        });
        this.addLayer(new SkullInHandLayer<>(this, context.getBlockRenderDispatcher()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                ArmorModelSet.bake(armorModelSet, context.getModelSet(), BabyWitherSkeletonModel::new),
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