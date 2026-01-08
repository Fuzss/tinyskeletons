package fuzs.tinyskeletons.client.renderer.entity;

import fuzs.tinyskeletons.client.model.geom.ModModelLayers;
import fuzs.tinyskeletons.client.packs.VanillaTexture;
import fuzs.tinyskeletons.client.renderer.entity.layers.ItemInMainHandLayer;
import fuzs.tinyskeletons.client.renderer.entity.layers.ItemOnBackLayer;
import fuzs.tinyskeletons.client.renderer.entity.state.BabySkeletonRenderState;
import fuzs.tinyskeletons.world.entity.monster.skeleton.BabySkeleton;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;

/**
 * @see net.minecraft.client.renderer.entity.SkeletonRenderer
 */
public class BabySkeletonRenderer extends AbstractSkeletonRenderer<BabySkeleton, BabySkeletonRenderState> {
    public static final VanillaTexture SKELETON_TEXTURE = new VanillaTexture("textures/entity/skeleton/skeleton.png");

    public BabySkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, ModModelLayers.BABY_SKELETON, ModModelLayers.BABY_SKELETON_ARMOR);
        this.layers.removeIf((RenderLayer<BabySkeletonRenderState, SkeletonModel<BabySkeletonRenderState>> renderLayer) -> {
            return renderLayer instanceof ItemInHandLayer;
        });
        this.addLayer(new ItemInMainHandLayer<>(this));
        this.addLayer(new ItemOnBackLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(BabySkeletonRenderState renderState) {
        return SKELETON_TEXTURE.id();
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
