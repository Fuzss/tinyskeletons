package fuzs.tinyskeletons.world.entity.monster;

import fuzs.tinyskeletons.world.entity.ai.goal.RangedBowEasyAttackGoal;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class BabySkeletonEntity extends Skeleton {
    private RangedBowAttackGoal<AbstractSkeleton> bowGoal;
    private MeleeAttackGoal meleeGoal;
    private int switchWeaponCooldown;

    public BabySkeletonEntity(EntityType<? extends Skeleton> type, Level world) {
        super(type, world);
        this.xpReward *= 2.5F;
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 0.93F;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.0;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(difficultyInstance);
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.WOODEN_SWORD));
        // back item shouldn't be dropped
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (this.switchWeaponCooldown > 0) this.switchWeaponCooldown--;
            if (this.switchWeaponCooldown == 0) {
                if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 4.0) {
                    if (this.getMainHandItem().getItem() instanceof BowItem) {
                        this.setHandItems(this.getOffhandItem(), this.getMainHandItem());
                        this.switchWeaponCooldown = 60;
                    }
                } else if (this.getTarget() == null || this.distanceTo(this.getTarget()) > 8.0) {
                    if (this.getMainHandItem().getItem() instanceof SwordItem) {
                        this.setHandItems(this.getOffhandItem(), this.getMainHandItem());
                        this.switchWeaponCooldown = 60;
                    }
                }
            }
        }
    }

    @Override
    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            if (this.bowGoal == null || this.meleeGoal == null) {
                this.createAttackGoals();
            }
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            if (this.getMainHandItem().getItem() instanceof BowItem) {
                int minAttackInterval = 20;
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    minAttackInterval = 40;
                }
                this.bowGoal.setMinAttackInterval(minAttackInterval);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }
        }
    }

    private void setHandItems(ItemStack mainHand, ItemStack offHand) {
        this.setItemInHand(InteractionHand.MAIN_HAND, mainHand);
        this.setItemInHand(InteractionHand.OFF_HAND, offHand);
    }

    private void createAttackGoals() {
        this.bowGoal = new RangedBowEasyAttackGoal<>(this, 1.0, 40, 60, 15.0F);
        this.meleeGoal = new MeleeAttackGoal(this, 1.2, false) {
            @Override
            public void stop() {
                super.stop();
                BabySkeletonEntity.this.setAggressive(false);
            }

            @Override
            public void start() {
                super.start();
                BabySkeletonEntity.this.setAggressive(true);
            }
        };
    }
}
