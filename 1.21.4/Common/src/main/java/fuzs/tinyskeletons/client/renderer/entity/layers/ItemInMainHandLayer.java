package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;

/**
 * An adaptation of {@link ItemInHandLayer} that does not render the off-hand item, so it can be handled separately.
 */
public class ItemInMainHandLayer<S extends ArmedEntityRenderState, M extends EntityModel<S> & ArmedModel> extends ItemInHandLayer<S, M> {

    public ItemInMainHandLayer(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        if (renderState.mainArm == HumanoidArm.RIGHT) {
            this.renderArmWithItem(renderState,
                    renderState.rightHandItem,
                    HumanoidArm.RIGHT,
                    poseStack,
                    bufferSource,
                    packedLight);
        } else {
            this.renderArmWithItem(renderState,
                    renderState.leftHandItem,
                    HumanoidArm.LEFT,
                    poseStack,
                    bufferSource,
                    packedLight);
        }
    }
}
