package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * An adaptation of {@link ItemInHandLayer} that does not render the off-hand item, so it can be handled separately.
 */
public class ItemInMainHandLayer<S extends LivingEntityRenderState, M extends EntityModel<S> & ArmedModel> extends ItemInHandLayer<S, M> {

    public ItemInMainHandLayer(RenderLayerParent<S, M> renderLayerParent, ItemRenderer itemRenderer) {
        super(renderLayerParent, itemRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, S livingEntityRenderState, float yRot, float xRot) {
        if (livingEntityRenderState.mainArm == HumanoidArm.RIGHT) {
            this.renderArmWithItem(livingEntityRenderState,
                    livingEntityRenderState.getMainHandItemModel(),
                    livingEntityRenderState.getMainHandItem(),
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    HumanoidArm.RIGHT,
                    poseStack,
                    multiBufferSource,
                    packedLight);
        } else {
            this.renderArmWithItem(livingEntityRenderState,
                    livingEntityRenderState.getMainHandItemModel(),
                    livingEntityRenderState.getMainHandItem(),
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    HumanoidArm.LEFT,
                    poseStack,
                    multiBufferSource,
                    packedLight);
        }
    }
}
