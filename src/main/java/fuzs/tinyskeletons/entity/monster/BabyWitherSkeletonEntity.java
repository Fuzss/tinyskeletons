package fuzs.tinyskeletons.entity.monster;

import fuzs.tinyskeletons.entity.ISkullCarryingMob;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class BabyWitherSkeletonEntity extends WitherSkeletonEntity implements ISkullCarryingMob {
    public BabyWitherSkeletonEntity(EntityType<? extends WitherSkeletonEntity> type, World level) {
        super(type, level);
        this.xpReward *= 2.5F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void reassessWeaponGoal() {

    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 1.1224137931F;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.0;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.WITHER_SKELETON_SKULL));
        this.setGuaranteedDrop(EquipmentSlotType.MAINHAND);
    }

    @Override
    public ItemStack getSkullItem() {
        if (this.isOnlyCarryingSkull(this, Hand.MAIN_HAND)) {
            return this.getMainHandItem();
        }
        if (this.isOnlyCarryingSkull(this, Hand.OFF_HAND)) {
            return this.getOffhandItem();
        }
        return ItemStack.EMPTY;
    }
}
