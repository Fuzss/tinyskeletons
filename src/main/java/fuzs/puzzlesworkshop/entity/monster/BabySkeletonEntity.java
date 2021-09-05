package fuzs.puzzlesworkshop.entity.monster;

import fuzs.puzzlesworkshop.entity.ai.goal.RangedBowEasyAttackGoal;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class BabySkeletonEntity extends SkeletonEntity {

    private RangedBowAttackGoal<AbstractSkeletonEntity> bowGoal;
    private MeleeAttackGoal meleeGoal;

    private int switchWeaponCooldown;

    public BabySkeletonEntity(EntityType<? extends SkeletonEntity> type, World world) {

        super(type, world);
        this.xpReward *= 2.5F;
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 0.93F;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.0;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(difficultyInstance);
        this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.WOODEN_SWORD));
    }

    @Override
    public void aiStep() {

        super.aiStep();
        if (!this.level.isClientSide) {

            if (this.switchWeaponCooldown > 0) {

                this.switchWeaponCooldown--;
            }

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

                int i = 20;
                if (this.level.getDifficulty() != Difficulty.HARD) {

                    i = 40;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {

                this.goalSelector.addGoal(4, this.meleeGoal);
            }
        }
    }

    private void setHandItems(ItemStack mainHand, ItemStack offHand) {

        this.setItemInHand(Hand.MAIN_HAND, mainHand);
        this.setItemInHand(Hand.OFF_HAND, offHand);
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
