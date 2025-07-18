package fuzs.tinyskeletons.client.model;

import fuzs.tinyskeletons.client.renderer.entity.state.BabyWitherSkeletonRenderState;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class BabyWitherSkeletonModel extends SkeletonModel<BabyWitherSkeletonRenderState> {

    public BabyWitherSkeletonModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(BabyWitherSkeletonRenderState entityRenderState) {
        super.setupAnim(entityRenderState);
        if (entityRenderState.renderCarryingSkull) {
            float f = Mth.sin(entityRenderState.attackTime * (float) Math.PI);
            float f1 = Mth.sin((1.0F - (1.0F - entityRenderState.attackTime) * (1.0F - entityRenderState.attackTime)) *
                    (float) Math.PI);
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightArm.yRot = -(0.1F - f * 0.6F);
            this.leftArm.yRot = 0.1F - f * 0.6F;
            this.rightArm.xRot = (-(float) Math.PI / 2F);
            this.leftArm.xRot = (-(float) Math.PI / 2F);
            this.rightArm.xRot -= f * 1.2F - f1 * 0.4F;
            this.leftArm.xRot -= f * 1.2F - f1 * 0.4F;
            // no call to bob arms
        }
        if (entityRenderState.isDancing) {
            float f3 = entityRenderState.ageInTicks / 60.0F;
            this.head.x = Mth.sin(f3 * 10.0F);
            this.head.y = Mth.sin(f3 * 40.0F) + 0.4F;
            this.rightArm.zRot = ((float) Math.PI / 180F) * (70.0F + Mth.cos(f3 * 40.0F) * 10.0F);
            this.leftArm.zRot = this.rightArm.zRot * -1.0F;
            this.rightArm.y = Mth.sin(f3 * 40.0F) * 0.5F + 1.5F;
            this.leftArm.y = Mth.sin(f3 * 40.0F) * 0.5F + 1.5F;
            this.body.y = Mth.sin(f3 * 40.0F) * 0.35F;
        }
    }
}
