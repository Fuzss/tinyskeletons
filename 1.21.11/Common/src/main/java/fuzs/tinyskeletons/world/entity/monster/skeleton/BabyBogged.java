package fuzs.tinyskeletons.world.entity.monster.skeleton;

import fuzs.tinyskeletons.init.ModRegistry;
import fuzs.tinyskeletons.util.BabySkeletonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.level.Level;

public class BabyBogged extends Bogged {

    public BabyBogged(EntityType<? extends Bogged> entityType, Level level) {
        super(entityType, level);
        this.xpReward = (int) (this.xpReward * 2.5F);
        this.refreshDimensions();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        BabySkeletonHelper.registerGoals(this, this.goalSelector, ModRegistry.BABY_BOGGED_THROWABLES_ITEM_TAG);
    }

    @Override
    public float getPickRadius() {
        return BabySkeletonHelper.DEFAULT_PICK_RADIUS;
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose)
                .withEyeHeight(this.getType().getDimensions().eyeHeight() * (this.isBaby() ?
                        BabySkeletonHelper.BABY_EYE_HEIGHT_SCALE : 1.0F));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(randomSource, difficulty);
        BabySkeletonHelper.setThrowableItemInMainHand(this, randomSource, ModRegistry.BABY_BOGGED_THROWABLES_ITEM_TAG);
    }

    @Override
    public void reassessWeaponGoal() {
        // NO-OP
    }

    @Override
    protected void resolveMobResponsibleForDamage(DamageSource damageSource) {
        if (BabySkeletonHelper.shouldBecomeAngry(damageSource.getEntity())) {
            super.resolveMobResponsibleForDamage(damageSource);
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        BabySkeletonHelper.performRangedAttack(this, target);
    }
}
