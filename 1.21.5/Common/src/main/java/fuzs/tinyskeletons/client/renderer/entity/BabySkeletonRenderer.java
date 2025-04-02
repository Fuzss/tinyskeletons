package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModelLayerLocations;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.client.renderer.entity.layers.ItemInMainHandLayer;
import fuzs.tinyskeletons.client.renderer.entity.layers.ItemOnBackLayer;
import fuzs.tinyskeletons.client.renderer.entity.state.BabySkeletonRenderState;
import fuzs.tinyskeletons.world.entity.monster.BabySkeleton;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.ResourceLocation;

public class BabySkeletonRenderer extends AbstractSkeletonRenderer<BabySkeleton, BabySkeletonRenderState> {
    public static final ResourceLocation SKELETON_LOCATION = TinySkeletons.id(BabySkeletonPackResources.SKELETON_LOCATION.getPath());

    public BabySkeletonRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModelLayerLocations.BABY_SKELETON,
                ModelLayerLocations.BABY_SKELETON_INNER_ARMOR,
                ModelLayerLocations.BABY_SKELETON_OUTER_ARMOR);
        this.layers.removeIf(renderLayer -> renderLayer instanceof ItemInHandLayer);
        this.addLayer(new ItemInMainHandLayer<>(this));
        this.addLayer(new ItemOnBackLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BabySkeletonRenderState renderState) {
        return SKELETON_LOCATION;
    }

    @Override
    public void extractRenderState(BabySkeleton babySkeleton, BabySkeletonRenderState renderState, float partialTick) {
        super.extractRenderState(babySkeleton, renderState, partialTick);
        renderState.offhandItemType = BabySkeletonRenderState.getItemType(babySkeleton.getOffhandItem().getItem());
        ItemStackRenderState itemStackRenderState = BabySkeletonRenderState.getOffHandItem(renderState);
        this.itemModelResolver.updateForLiving(itemStackRenderState,
                babySkeleton.getItemHeldByArm(renderState.mainArm.getOpposite()),
                renderState.offhandItemType.getItemDisplayContext(),
                babySkeleton);
    }

    @Override
    public BabySkeletonRenderState createRenderState() {
        return new BabySkeletonRenderState();
    }
}