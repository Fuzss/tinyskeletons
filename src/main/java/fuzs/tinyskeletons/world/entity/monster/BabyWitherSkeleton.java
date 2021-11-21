package fuzs.tinyskeletons.world.entity.monster;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BabyWitherSkeleton extends WitherSkeleton implements SkullCarryingMob {
    public BabyWitherSkeleton(EntityType<? extends WitherSkeleton> type, Level level) {
        super(type, level);
        this.xpReward *= 2.5F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, IronGolem.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void reassessWeaponGoal() {

    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 1.1224137931F;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.0;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WITHER_SKELETON_SKULL));
        this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
    }

    @Override
    public ItemStack getSkullItem() {
        if (this.isOnlyCarryingSkull(this, InteractionHand.MAIN_HAND)) {
            return this.getMainHandItem();
        }
        if (this.isOnlyCarryingSkull(this, InteractionHand.OFF_HAND)) {
            return this.getOffhandItem();
        }
        return ItemStack.EMPTY;
    }
}
