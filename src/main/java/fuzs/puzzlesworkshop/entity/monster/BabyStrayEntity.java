package fuzs.puzzlesworkshop.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BabyStrayEntity extends StrayEntity {
    public BabyStrayEntity(EntityType<? extends StrayEntity> p_i50191_1_, World p_i50191_2_) {
        super(p_i50191_1_, p_i50191_2_);
        this.xpReward *= 2.5F;
    }

    public static boolean checkBabyStraySpawnRules(EntityType<BabyStrayEntity> p_223327_0_, IServerWorld p_223327_1_, SpawnReason p_223327_2_, BlockPos p_223327_3_, Random p_223327_4_) {
        return checkMonsterSpawnRules(p_223327_0_, p_223327_1_, p_223327_2_, p_223327_3_, p_223327_4_) && (p_223327_2_ == SpawnReason.SPAWNER || p_223327_1_.canSeeSky(p_223327_3_));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 30, 15.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && BabyStrayEntity.this.getMainHandItem().getItem() == Items.SNOWBALL;
            }
        });
        super.registerGoals();
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
        // super call for armor
        super.populateDefaultEquipmentSlots(difficultyInstance);
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.SNOWBALL));
    }

    @Override
    public void reassessWeaponGoal() {

    }

    @Override
    public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
        SnowballEntity snowballentity = new SnowballEntity(this.level, this) {
            @Override
            protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
                Entity entity = p_213868_1_.getEntity();
                entity.hurt(DamageSource.thrown(this, this.getOwner()), 1.0F);
            }
        };
        double d0 = p_82196_1_.getEyeY() - (double)1.1F;
        double d1 = p_82196_1_.getX() - this.getX();
        double d2 = d0 - snowballentity.getY();
        double d3 = p_82196_1_.getZ() - this.getZ();
        double f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2;
        snowballentity.shoot(d1, d2 + f, d3, 1.6F, 14.0F - this.level.getDifficulty().getId() * 2.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(snowballentity);
        this.swing(Hand.MAIN_HAND);
    }
}
