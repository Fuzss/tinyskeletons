package fuzs.tinyskeletons.world.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;

public class RangedBowEasyAttackGoal<T extends Monster & RangedAttackMob> extends RangedBowAttackGoal<T> {
    private final T entity;
    private final double moveSpeedAmplifier;
    private int attackCooldown;
    private final int maxAttackTime;
    private final float maxAttackDistance;
    private int attackTime = -1;
    private int seeTime;

    public RangedBowEasyAttackGoal(T mob, double chaseTargetSpeed, int attackCooldown, int maxAttackTime, float maxAttackDistance) {
        super(mob, chaseTargetSpeed, attackCooldown, maxAttackDistance);
        this.entity = mob;
        this.moveSpeedAmplifier = chaseTargetSpeed;
        this.attackCooldown = attackCooldown;
        this.maxAttackTime = maxAttackTime;
        this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
    }

    @Override
    public void setMinAttackInterval(int attackCooldownIn) {
        this.attackCooldown = attackCooldownIn;
    }

    @Override
    public void stop() {
        this.entity.setAggressive(false);
        this.seeTime = 0;
        this.attackTime = -1;
        this.entity.stopUsingItem();
    }

    @Override
    public void tick() {
        LivingEntity attackTarget = this.entity.getTarget();
        if (attackTarget != null) {
            double distanceToTarget = this.entity.distanceToSqr(attackTarget.getX(), attackTarget.getY(), attackTarget.getZ());
            boolean canSeeTarget = this.entity.getSensing().hasLineOfSight(attackTarget);
            if (canSeeTarget != this.seeTime > 0) {
                this.seeTime = 0;
            }
            if (canSeeTarget) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }
            boolean moveTowardsTarget = false;
            if (distanceToTarget <= this.maxAttackDistance && this.seeTime >= 20) {
                this.entity.getNavigation().stop();
                moveTowardsTarget = distanceToTarget > this.maxAttackDistance * 0.75F;
            } else {
                this.entity.getNavigation().moveTo(attackTarget, this.moveSpeedAmplifier);
            }
            // force skeleton to move towards target, tryMoveToEntityLiving sometimes isn't good enough and leads to the attack being cancelled
            if (moveTowardsTarget) {
                this.entity.getMoveControl().strafe(0.5F, 0.0F);
                this.entity.lookAt(attackTarget, 30.0F, 30.0F);
            } else {
                this.entity.getLookControl().setLookAt(attackTarget, 30.0F, 30.0F);
            }
            if (this.entity.isUsingItem()) {
                if (!canSeeTarget && this.seeTime < -this.maxAttackTime) {
                    this.entity.stopUsingItem();
                } else if (canSeeTarget) {
                    int useCount = this.entity.getTicksUsingItem();
                    if (useCount >= 20) {
                        this.entity.stopUsingItem();
                        double distanceVelocity = Math.sqrt(distanceToTarget) / Math.sqrt(this.maxAttackDistance);
                        this.entity.performRangedAttack(attackTarget, Mth.clamp((float) distanceVelocity, 0.1F, 1.0F) * BowItem.getPowerForTime(useCount));
                        this.attackTime = Mth.floor(distanceVelocity * (this.maxAttackTime - this.attackCooldown / 2.0F) + this.attackCooldown / 2.0F);
                    }
                }
            } else if (--this.attackTime <= 0 && this.seeTime >= -this.maxAttackTime) {
                this.entity.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.entity, Items.BOW));
            }
        }
    }
}