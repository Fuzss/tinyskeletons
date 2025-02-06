package fuzs.tinyskeletons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

public class ItemOnBackLayer<S extends HumanoidRenderState, M extends HumanoidModel<S> & ArmedModel> extends RenderLayer<S, M> {
    private final ItemRenderer itemRenderer;

    public ItemOnBackLayer(RenderLayerParent<S, M> renderLayerParent, ItemRenderer itemRenderer) {
        super(renderLayerParent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, S entityRenderState, float yRot, float xRot) {
        ItemStack itemstack = getOffHandItem(entityRenderState);
        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            ModelPart modelPart = this.getParentModel().body;
            modelPart.translateAndRotate(poseStack);
            float scale = 2.0F;
            float backOffset = !entityRenderState.chestItem.isEmpty() ? 0.24F : 0.18F;
            ItemDisplayContext itemDisplayContext;
            if (!(itemstack.getItem() instanceof TridentItem)) {
                poseStack.translate(0.0D, 0.0D, backOffset);
                poseStack.scale(scale, scale, scale);
                if (itemstack.getItem() instanceof FishingRodItem || itemstack.getItem() instanceof FoodOnAStickItem) {
                    poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                    poseStack.translate(0.0, -0.3, 0.0);
                }
                itemDisplayContext = ItemDisplayContext.GROUND;
            } else {
                scale *= 0.5F;
                poseStack.mulPose(Axis.YP.rotationDegrees(52.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(40.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-25.0F));
                poseStack.scale(scale, -scale, -scale);
                poseStack.translate(-backOffset, 0.0, 0.0);
                itemDisplayContext = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }
            this.itemRenderer.render(itemstack,
                    itemDisplayContext,
                    false,
                    poseStack,
                    multiBufferSource,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    getOffHandItemModel(entityRenderState));
            poseStack.popPose();
        }
    }

    static ItemStack getOffHandItem(LivingEntityRenderState entityRenderState) {
        return entityRenderState.mainArm != HumanoidArm.RIGHT ? entityRenderState.rightHandItem :
                entityRenderState.leftHandItem;
    }

    @Nullable
    static BakedModel getOffHandItemModel(LivingEntityRenderState entityRenderState) {
        return entityRenderState.mainArm != HumanoidArm.RIGHT ? entityRenderState.rightHandItemModel :
                entityRenderState.leftHandItemModel;
    }
}
