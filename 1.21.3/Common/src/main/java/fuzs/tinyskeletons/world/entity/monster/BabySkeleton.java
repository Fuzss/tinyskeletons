package fuzs.tinyskeletons.world.entity.monster;

import fuzs.tinyskeletons.init.ModRegistry;
import fuzs.tinyskeletons.mixin.accessor.LivingEntityAccessor;
import fuzs.tinyskeletons.world.entity.ai.goal.RangedBowEasyAttackGoal;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;

public class BabySkeleton extends Skeleton {
    private RangedBowAttackGoal<AbstractSkeleton> bowGoal;
    private MeleeAttackGoal meleeGoal;
    private int switchWeaponCooldown;

    public BabySkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
        this.xpReward = (int) (this.xpReward * 2.5F);
        this.refreshDimensions();
    }

    @Override
    public float getPickRadius() {
        return 0.3F;
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose)
                .withEyeHeight(this.getType().getDimensions().eyeHeight() * (this.isBaby() ? 0.534F : 1.0F));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.WOODEN_SWORD));
        // back item shouldn't be dropped
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    @Override
    protected void doFreezeConversion() {
        this.convertTo(ModRegistry.BABY_STRAY_ENTITY_TYPE.value(), ConversionParams.single(this, true, true), stray -> {
            // need this call as otherwise overriding with empty items will not send an update to clients as empty is default value and all this is happening within the same tick
            // (LivingEntity::detectEquipmentUpdates is called in the tick method)
            ((LivingEntityAccessor) stray).tinyskeletons$callDetectEquipmentUpdates();
            // cheap hack so we don't have to implement proper fighting behavior for strays, just give them their default snowball and remove everything else
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                stray.setItemSlot(slot, ItemStack.EMPTY);
            }
            stray.populateDefaultEquipmentSlots(this.random,
                    stray.level().getCurrentDifficultyAt(stray.blockPosition()));
            if (!this.isSilent()) {
                this.level().levelEvent(null, LevelEvent.SOUND_SKELETON_TO_STRAY, this.blockPosition(), 0);
            }
        });
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
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
        if (this.level() != null && !this.level().isClientSide) {
            if (this.bowGoal == null || this.meleeGoal == null) {
                this.createAttackGoals();
            }
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            if (this.getMainHandItem().getItem() instanceof BowItem) {
                int minAttackInterval = 20;
                if (this.level().getDifficulty() != Difficulty.HARD) {
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
                BabySkeleton.this.setAggressive(false);
            }

            @Override
            public void start() {
                super.start();
                BabySkeleton.this.setAggressive(true);
            }
        };
    }
}
