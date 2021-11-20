package fuzs.puzzlesworkshop.client.renderer.entity.model;

import fuzs.puzzlesworkshop.entity.ISkullCarryingMob;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;

public class SkullCarryingSkeletonModel<T extends MobEntity & IRangedAttackMob & ISkullCarryingMob> extends SkeletonModel<T> {
    public SkullCarryingSkeletonModel() {
        super();
    }

    public SkullCarryingSkeletonModel(float p_i46303_1_, boolean p_i46303_2_) {
        super(p_i46303_1_, p_i46303_2_);
    }

    @Override
    public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
        if (p_225597_1_.renderCarryingSkull()) {
            float f = MathHelper.sin(this.attackTime * (float)Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float)Math.PI);
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightArm.yRot = -(0.1F - f * 0.6F);
            this.leftArm.yRot = 0.1F - f * 0.6F;
            this.rightArm.xRot = (-(float)Math.PI / 2F);
            this.leftArm.xRot = (-(float)Math.PI / 2F);
            this.rightArm.xRot -= f * 1.2F - f1 * 0.4F;
            this.leftArm.xRot -= f * 1.2F - f1 * 0.4F;
            // no call to bob arms
        }
    }
}
