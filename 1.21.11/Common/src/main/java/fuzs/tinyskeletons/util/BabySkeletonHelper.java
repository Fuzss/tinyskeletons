package fuzs.tinyskeletons.util;

import fuzs.tinyskeletons.world.entity.monster.projectile.throwableitemprojectile.HurtingItemProjectile;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class BabySkeletonHelper {
    public static final float BABY_EYE_HEIGHT_SCALE = 0.534F;
    public static final float DEFAULT_PICK_RADIUS = 0.3F;

    public static AttributeSupplier.Builder applyCommonAttributes(AttributeSupplier.Builder builder) {
        return builder.add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    public static void registerGoals(AbstractSkeleton abstractSkeleton, GoalSelector goalSelector, TagKey<Item> tagKey) {
        goalSelector.addGoal(4, new RangedAttackGoal(abstractSkeleton, 1.0, 30, 15.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && abstractSkeleton.getMainHandItem().is(tagKey);
            }
        });
    }

    public static void setThrowableItemInMainHand(AbstractSkeleton abstractSkeleton, RandomSource randomSource, TagKey<Item> tagKey) {
        abstractSkeleton.level()
                .registryAccess()
                .lookupOrThrow(Registries.ITEM)
                .getRandomElementOf(tagKey, randomSource)
                .ifPresent((Holder<Item> holder) -> {
                    abstractSkeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(holder));
                });
    }

    public static boolean shouldBecomeAngry(@Nullable Entity entity) {
        return entity == null || !entity.getType().is(EntityTypeTags.SKELETONS);
    }

    public static void performRangedAttack(AbstractSkeleton abstractSkeleton, LivingEntity target) {
        ItemStack itemStack = abstractSkeleton.getMainHandItem();
        if (!itemStack.isEmpty()) {
            double dX = target.getX() - abstractSkeleton.getX();
            double dY = target.getEyeY() - 1.1F;
            double dZ = target.getZ() - abstractSkeleton.getZ();
            double g = Math.sqrt(dX * dX + dZ * dZ) * 0.2F;
            if (abstractSkeleton.level() instanceof ServerLevel serverLevel) {
                Projectile.spawnProjectile(new HurtingItemProjectile(serverLevel, abstractSkeleton, itemStack),
                        serverLevel,
                        itemStack,
                        (HurtingItemProjectile hurtingItemProjectile) -> {
                            hurtingItemProjectile.shoot(dX,
                                    dY + g - hurtingItemProjectile.getY(),
                                    dZ,
                                    1.6F,
                                    14.0F - serverLevel.getDifficulty().getId() * 2.0F);
                        });
            }

            abstractSkeleton.playSound(SoundEvents.SNOW_GOLEM_SHOOT,
                    1.0F,
                    0.4F / (abstractSkeleton.getRandom().nextFloat() * 0.4F + 0.8F));
            abstractSkeleton.swing(InteractionHand.MAIN_HAND);
        }
    }
}
