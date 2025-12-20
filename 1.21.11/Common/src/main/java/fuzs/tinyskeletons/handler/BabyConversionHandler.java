package fuzs.tinyskeletons.handler;

import com.google.common.collect.ImmutableMap;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import fuzs.puzzleslib.api.util.v1.EntityHelper;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class BabyConversionHandler {
    private static final Map<EntityType<?>, Holder<EntityType<?>>> BABY_SKELETON_VARIANTS = ImmutableMap.of(EntityType.SKELETON,
            (Holder<EntityType<?>>) ((Holder<?>) ModRegistry.BABY_SKELETON_ENTITY_TYPE),
            EntityType.WITHER_SKELETON,
            (Holder<EntityType<?>>) ((Holder<?>) ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE),
            EntityType.STRAY,
            (Holder<EntityType<?>>) ((Holder<?>) ModRegistry.BABY_STRAY_ENTITY_TYPE),
            EntityType.BOGGED,
            (Holder<EntityType<?>>) ((Holder<?>) ModRegistry.BABY_BOGGED_ENTITY_TYPE),
            EntityType.PARCHED,
            (Holder<EntityType<?>>) ((Holder<?>) ModRegistry.BABY_PARCHED_ENTITY_TYPE));

    public static EventResult onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isNewlySpawned) {
        // exclude summoned by command as this would break forcefully spawning an adult skeleton
        // since there is no baby flag as with zombies which could force that otherwise
        EntitySpawnReason entitySpawnReason = EntityHelper.getMobSpawnReason(entity);
        if (isNewlySpawned && entitySpawnReason != null && entitySpawnReason != EntitySpawnReason.COMMAND
                && Zombie.getSpawnAsBabyOdds(serverLevel.getRandom())) {
            Holder<EntityType<?>> holder = BABY_SKELETON_VARIANTS.get(entity.getType());
            if (holder != null
                    && createAndSpawnBabyMob(serverLevel, holder.value(), entity, entitySpawnReason) != null) {

                return EventResult.INTERRUPT;
            }
        }

        return EventResult.PASS;
    }

    public static EventResultHolder<InteractionResult> onEntityInteract(Player player, Level level, InteractionHand interactionHand, Entity target, Vec3 hitVector) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (target.isAlive() && itemInHand.getItem() instanceof SpawnEggItem) {
            EntityType<?> entityType = ((SpawnEggItem) itemInHand.getItem()).getType(itemInHand);
            Holder<EntityType<?>> holder = BABY_SKELETON_VARIANTS.get(entityType);
            if (holder != null && (target.getType() == holder.value() || target.getType() == entityType)) {
                if (level instanceof ServerLevel serverLevel) {
                    Entity entity = createAndSpawnBabyMob(serverLevel,
                            holder.value(),
                            target,
                            EntitySpawnReason.SPAWN_ITEM_USE);
                    if (entity instanceof Mob mob) {
                        finalizeSpawnEggMob(mob, itemInHand, player);
                    }
                }

                return EventResultHolder.interrupt(InteractionResult.SUCCESS);
            }
        }

        return EventResultHolder.pass();
    }

    private static void finalizeSpawnEggMob(Mob mob, ItemStack itemStack, Player player) {
        mob.playAmbientSound();
        if (itemStack.has(DataComponents.CUSTOM_NAME)) {
            mob.setCustomName(itemStack.getHoverName());
        }

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
    }

    @Nullable
    private static Entity createAndSpawnBabyMob(ServerLevel serverLevel, EntityType<?> entityType, Entity parent, EntitySpawnReason entitySpawnReason) {
        Entity entity;
        if (parent instanceof AgeableMob ageableMob) {
            entity = ageableMob.getBreedOffspring(serverLevel, ageableMob);
        } else {
            entity = entityType.create(serverLevel, entitySpawnReason);
        }

        if (entity == null) {
            return null;
        }

        entity.snapTo(parent.getX(),
                parent.getY(),
                parent.getZ(),
                Mth.wrapDegrees(serverLevel.random.nextFloat() * 360.0F),
                0.0F);
        if (entity instanceof Mob mob) {
            mob.yHeadRot = entity.getYRot();
            mob.yBodyRot = entity.getYRot();
            DifficultyInstance difficulty = serverLevel.getCurrentDifficultyAt(parent.blockPosition());
            mob.finalizeSpawn(serverLevel, difficulty, entitySpawnReason, null);
        }

        serverLevel.addFreshEntityWithPassengers(entity);
        return entity;
    }
}
