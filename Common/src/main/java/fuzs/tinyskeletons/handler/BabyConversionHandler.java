package fuzs.tinyskeletons.handler;

import com.google.common.collect.Maps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BabyConversionHandler {
    private static final Map<EntityType<? extends Mob>, EntityType<? extends Mob>> BABY_MOB_CONVERSIONS = Maps.newHashMap();

    public static void registerConversion(EntityType<? extends Mob> targetMob, EntityType<? extends Mob> convertsTo) {
        BABY_MOB_CONVERSIONS.put(targetMob, convertsTo);
    }

    public static boolean onMobCreate(LevelAccessor level, Entity entity, @Nullable MobSpawnType spawnReason) {
        // spawner type shouldn't end up here anyway, but just to make sure
        // would break balancing for baby wither skeletons
        // also exclude summoned by command as this would break forcefully spawning an adult skeleton since there is no baby flag as with zombies which could force that otherwise
        if (spawnReason != MobSpawnType.SPAWNER && spawnReason != MobSpawnType.COMMAND) {
            if (level instanceof ServerLevel serverLevel && Zombie.getSpawnAsBabyOdds(serverLevel.getRandom())) {
                EntityType<? extends Mob> babyType = BABY_MOB_CONVERSIONS.get(entity.getType());
                if (babyType != null) return makeBabyMob(serverLevel, babyType, entity, spawnReason).isPresent();
            }
        }
        return false;
    }

    public static InteractionResult onEntityInteract(Player player, Level level, InteractionHand hand, Entity target) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (target.isAlive() && itemstack.getItem() instanceof SpawnEggItem) {
            EntityType<?> eggType = ((SpawnEggItem) itemstack.getItem()).getType(itemstack.getTag());
            EntityType<? extends Mob> babyType = BABY_MOB_CONVERSIONS.get(eggType);
            if (babyType != null && (target.getType() == babyType || target.getType() == eggType)) {
                if (!level.isClientSide) {
                    final Optional<Mob> mob = makeBabyMob((ServerLevel) level, babyType, target, MobSpawnType.SPAWN_EGG);
                    if (mob.isPresent()) {
                        finalizeSpawnEggMob(mob.get(), itemstack, player);
                        return InteractionResult.CONSUME;
                    }
                    return InteractionResult.PASS;
                } else {
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return null;
    }

    private static void finalizeSpawnEggMob(Mob mob, ItemStack itemstack, Player player) {
        mob.playAmbientSound();
        if (itemstack.hasCustomHoverName()) {
            mob.setCustomName(itemstack.getHoverName());
        }
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
    }

    private static Optional<Mob> makeBabyMob(ServerLevel level, EntityType<? extends Mob> entityType, Entity parent, MobSpawnType spawnReason) {
        Mob mob;
        if (parent instanceof AgeableMob ageableMob) {
            mob = ageableMob.getBreedOffspring(level, ageableMob);
        } else {
            mob = entityType.create(level);
        }
        if (mob == null) {
            return Optional.empty();
        }
        if (!mob.isBaby()) {
            throw new RuntimeException("Baby mob must be a baby by default");
        }
        mob.moveTo(parent.getX(), parent.getY(), parent.getZ(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
        mob.yHeadRot = mob.getYRot();
        mob.yBodyRot = mob.getYRot();
        mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), spawnReason, null, null);
        level.addFreshEntityWithPassengers(mob);
        return Optional.of(mob);
    }
}
