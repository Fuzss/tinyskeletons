package fuzs.tinyskeletons.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.EntityHitResult;

public class BabyStray extends Stray {

    public BabyStray(EntityType<? extends Stray> entityType, Level level) {
        super(entityType, level);
        this.xpReward = (int) (this.xpReward * 2.5F);
        this.refreshDimensions();
    }

    public static boolean checkBabyStraySpawnRules(EntityType<BabyStray> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return checkMonsterSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) &&
                (mobSpawnType == MobSpawnType.SPAWNER || serverLevelAccessor.canSeeSky(blockPos));
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
        // super call for armor
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SNOWBALL));
    }

    @Override
    public void reassessWeaponGoal() {
        // NO-OP
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Snowball snowball = new Snowball(this.level(), this) {

            @Override
            protected void onHitEntity(EntityHitResult hitResult) {
                Entity entity = hitResult.getEntity();
                entity.hurt(this.level().damageSources().thrown(this, this.getOwner()), 0.5F);
            }
        };
        double d0 = target.getEyeY() - (double) 1.1F;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - snowball.getY();
        double d3 = target.getZ() - this.getZ();
        double f = Math.sqrt(d1 * d1 + d3 * d3) * 0.2;
        snowball.shoot(d1, d2 + f, d3, 1.6F, 14.0F - this.level().getDifficulty().getId() * 2.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(snowball);
        this.swing(InteractionHand.MAIN_HAND);
    }
}
