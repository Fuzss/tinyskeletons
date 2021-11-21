package fuzs.tinyskeletons.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Random;

public class BabyStray extends Stray {
    public BabyStray(EntityType<? extends Stray> p_i50191_1_, Level p_i50191_2_) {
        super(p_i50191_1_, p_i50191_2_);
        this.xpReward *= 2.5F;
    }

    public static boolean checkBabyStraySpawnRules(EntityType<BabyStray> p_223327_0_, ServerLevelAccessor p_223327_1_, MobSpawnType p_223327_2_, BlockPos p_223327_3_, Random p_223327_4_) {
        return checkMonsterSpawnRules(p_223327_0_, p_223327_1_, p_223327_2_, p_223327_3_, p_223327_4_) && (p_223327_2_ == MobSpawnType.SPAWNER || p_223327_1_.canSeeSky(p_223327_3_));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 30, 15.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && BabyStray.this.getMainHandItem().getItem() == Items.SNOWBALL;
            }
        });
        super.registerGoals();
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
        // super call for armor
        super.populateDefaultEquipmentSlots(difficultyInstance);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SNOWBALL));
    }

    @Override
    public void reassessWeaponGoal() {

    }

    @Override
    public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
        Snowball snowballentity = new Snowball(this.level, this) {
            @Override
            protected void onHitEntity(EntityHitResult p_213868_1_) {
                Entity entity = p_213868_1_.getEntity();
                entity.hurt(DamageSource.thrown(this, this.getOwner()), 1.0F);
            }
        };
        double d0 = p_82196_1_.getEyeY() - (double)1.1F;
        double d1 = p_82196_1_.getX() - this.getX();
        double d2 = d0 - snowballentity.getY();
        double d3 = p_82196_1_.getZ() - this.getZ();
        double f = Math.sqrt(d1 * d1 + d3 * d3) * 0.2;
        snowballentity.shoot(d1, d2 + f, d3, 1.6F, 14.0F - this.level.getDifficulty().getId() * 2.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(snowballentity);
        this.swing(InteractionHand.MAIN_HAND);
    }
}
