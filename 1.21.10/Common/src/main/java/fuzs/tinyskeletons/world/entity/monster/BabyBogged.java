package fuzs.tinyskeletons.world.entity.monster;

import fuzs.tinyskeletons.world.entity.monster.projectile.Mushroom;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BabyBogged extends Bogged {

    public BabyBogged(EntityType<? extends Bogged> entityType, Level level) {
        super(entityType, level);
        this.xpReward = (int) (this.xpReward * 2.5F);
        this.refreshDimensions();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 30, 15.0F) {

            @Override
            public boolean canUse() {
                return super.canUse() && BabyBogged.this.getMainHandItem().isEmpty();
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
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    public void reassessWeaponGoal() {
        // NO-OP
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        double dX = target.getX() - this.getX();
        double dY = target.getEyeY() - 1.1F;
        double dZ = target.getZ() - this.getZ();
        double g = Math.sqrt(dX * dX + dZ * dZ) * 0.2F;
        if (this.level() instanceof ServerLevel serverLevel) {
            ItemStack itemStack = new ItemStack(this.random.nextBoolean() ? Items.RED_MUSHROOM : Items.BROWN_MUSHROOM);
            Projectile.spawnProjectile(new Mushroom(serverLevel, this, itemStack),
                    serverLevel,
                    itemStack,
                    snowball -> snowball.shoot(dX,
                            dY + g - snowball.getY(),
                            dZ,
                            1.6F,
                            14.0F - serverLevel.getDifficulty().getId() * 2.0F));
        }

        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.swing(InteractionHand.MAIN_HAND);
    }
}
