package fuzs.tinyskeletons.world.entity.monster;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BabyWitherSkeleton extends WitherSkeleton implements SkullCarryingMob {
    private final AvoidEntityGoal<Player> fleePlayerGoal = new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0D, 1.2D);
    private int dancingTicks;

    public BabyWitherSkeleton(EntityType<? extends WitherSkeleton> entityType, Level level) {
        super(entityType, level);
        this.xpReward = (int) (this.xpReward * 2.5F);
        this.refreshDimensions();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, IronGolem.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public float getPickRadius() {
        return 0.35F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.dancingTicks > 0) {
            this.dancingTicks--;
        }
    }

    @Override
    public void reassessWeaponGoal() {
        if (this.level() != null && !this.level().isClientSide()) {
            this.goalSelector.removeGoal(this.fleePlayerGoal);
            if (!this.getSkullItem().isEmpty()) {
                this.goalSelector.addGoal(3, this.fleePlayerGoal);
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (itemstack.is(Items.WITHER_ROSE)) {
            ItemStack skullItem = this.getSkullItem();
            if (!skullItem.isEmpty()) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.spawnAtLocation(serverLevel, skullItem);
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }

                this.setDancing();
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, interactionHand);
    }

    private void setDancing() {
        this.dancingTicks = 300;
    }

    @Override
    public boolean isDancing() {
        return this.dancingTicks > 0;
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
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WITHER_SKELETON_SKULL));
        this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
    }

    @Override
    protected void resolveMobResponsibleForDamage(DamageSource damageSource) {
        if (!damageSource.getEntity().getType().is(EntityTypeTags.SKELETONS)) {
            super.resolveMobResponsibleForDamage(damageSource);
        }
    }

    @Override
    public ItemStack getSkullItem() {
        if (this.isOnlyCarryingSkull(this, InteractionHand.MAIN_HAND)) {
            return this.getMainHandItem();
        } else if (this.isOnlyCarryingSkull(this, InteractionHand.OFF_HAND)) {
            return this.getOffhandItem();
        } else {
            return ItemStack.EMPTY;
        }
    }
}
