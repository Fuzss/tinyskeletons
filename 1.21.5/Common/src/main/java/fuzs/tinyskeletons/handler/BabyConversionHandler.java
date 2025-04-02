package fuzs.tinyskeletons.handler;

import com.google.common.base.Preconditions;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BabyConversionHandler {
    private static final Map<EntityType<? extends Mob>, EntityType<? extends Mob>> BABY_MOB_CONVERSIONS = new HashMap<>();

    public static void registerConversion(EntityType<? extends Mob> targetMob, EntityType<? extends Mob> convertsTo) {
        BABY_MOB_CONVERSIONS.put(targetMob, convertsTo);
    }

    public static EventResult onEntitySpawn(Entity entity, ServerLevel serverLevel, @Nullable EntitySpawnReason entitySpawnReason) {
        // exclude summoned by command as this would break forcefully spawning an adult skeleton since there is no baby flag as with zombies which could force that otherwise
        if (entitySpawnReason != null && entitySpawnReason != EntitySpawnReason.COMMAND &&
                Zombie.getSpawnAsBabyOdds(serverLevel.getRandom())) {
            EntityType<? extends Mob> entityType = BABY_MOB_CONVERSIONS.get(entity.getType());
            if (entityType != null &&
                    createAndSpawnBabyMob(serverLevel, entityType, entity, entitySpawnReason) != null) {

                return EventResult.INTERRUPT;
            }
        }

        return EventResult.PASS;
    }

    public static EventResultHolder<InteractionResult> onEntityInteract(Player player, Level level, InteractionHand interactionHand, Entity target, Vec3 hitVector) {
        ItemStack stackInHand = player.getItemInHand(interactionHand);
        if (target.isAlive() && stackInHand.getItem() instanceof SpawnEggItem) {
            EntityType<?> eggType = ((SpawnEggItem) stackInHand.getItem()).getType(level.registryAccess(), stackInHand);
            EntityType<? extends Mob> babyType = BABY_MOB_CONVERSIONS.get(eggType);
            if (babyType != null && (target.getType() == babyType || target.getType() == eggType)) {
                if (!level.isClientSide) {
                    Mob mob = createAndSpawnBabyMob((ServerLevel) level,
                            babyType,
                            target,
                            EntitySpawnReason.SPAWN_ITEM_USE);
                    if (mob != null) {
                        finalizeSpawnEggMob(mob, stackInHand, player);
                    }
                }

                return EventResultHolder.interrupt(InteractionResultHelper.sidedSuccess(level.isClientSide));
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
    private static Mob createAndSpawnBabyMob(ServerLevel serverLevel, EntityType<? extends Mob> entityType, Entity parent, EntitySpawnReason entitySpawnReason) {
        Mob mob;
        if (parent instanceof AgeableMob ageableMob) {
            mob = ageableMob.getBreedOffspring(serverLevel, ageableMob);
        } else {
            mob = entityType.create(serverLevel, entitySpawnReason);
        }
        if (mob == null) return null;
        Preconditions.checkState(mob.isBaby(), "Baby mob must be a baby by default");
        mob.snapTo(parent.getX(),
                parent.getY(),
                parent.getZ(),
                Mth.wrapDegrees(serverLevel.random.nextFloat() * 360.0F),
                0.0F);
        mob.yHeadRot = mob.getYRot();
        mob.yBodyRot = mob.getYRot();
        // this should normally be level.getCurrentDifficultyAt(mob.blockPosition()), which for some reason causes a world gen deadlock when generating nether fortresses for some setups (cannot reproduce in vanilla)
        // the deadlock comes from Level::getChunkAt, so we omit that part all call everything else as usual since the chunk otherwise is not queried
        DifficultyInstance difficulty = new DifficultyInstance(serverLevel.getDifficulty(),
                serverLevel.getDayTime(),
                0L,
                serverLevel.getMoonBrightness());
        mob.finalizeSpawn(serverLevel, difficulty, entitySpawnReason, null);
        serverLevel.addFreshEntityWithPassengers(mob);
        return mob;
    }
}
