package fuzs.tinyskeletons.handler;

import com.google.common.collect.Maps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Map;
import java.util.Optional;

public class BabyConversionHandler {
    private static final Map<EntityType<? extends Mob>, EntityType<? extends Mob>> BABY_MOB_CONVERSIONS = Maps.newHashMap();

    public static void registerConversion(EntityType<? extends Mob> targetMob, EntityType<? extends Mob> convertsTo) {
        BABY_MOB_CONVERSIONS.put(targetMob, convertsTo);
    }

    public void onSpecialSpawn(final LivingSpawnEvent.SpecialSpawn evt) {
        if (evt.getWorld() instanceof ServerLevel level && Zombie.getSpawnAsBabyOdds(evt.getWorld().getRandom())) {
            EntityType<? extends Mob> babyType = BABY_MOB_CONVERSIONS.get(evt.getEntity().getType());
            if (babyType != null) {
                makeBabyMob(level, babyType, evt.getEntity(), evt.getSpawnReason()).ifPresent(mobentity -> evt.setCanceled(true));
            }
        }
    }

    public void onEntityInteract(final PlayerInteractEvent.EntityInteract evt) {
        final Entity target = evt.getTarget();
        if (target.isAlive() && evt.getItemStack().getItem() instanceof SpawnEggItem) {
            ItemStack itemstack = evt.getItemStack();
            EntityType<?> eggType = ((SpawnEggItem) itemstack.getItem()).getType(itemstack.getTag());
            EntityType<? extends Mob> babyType = BABY_MOB_CONVERSIONS.get(eggType);
            if (babyType != null && (target.getType() == babyType || target.getType() == eggType)) {
                evt.setCanceled(true);
                if (evt.getWorld() instanceof ServerLevel level) {
                    final Optional<Mob> mob = makeBabyMob(level, babyType, target, MobSpawnType.SPAWN_EGG);
                    if (mob.isPresent()) {
                        this.finalizeSpawnEggMob(mob.get(), itemstack, evt.getPlayer());
                        evt.setCancellationResult(InteractionResult.SUCCESS);
                        return;
                    }
                    evt.setCancellationResult(InteractionResult.PASS);
                } else {
                    evt.setCancellationResult(InteractionResult.CONSUME);
                }
            }
        }
    }

    private void finalizeSpawnEggMob(Mob mobentity, ItemStack itemstack, Player player) {
        mobentity.playAmbientSound();
        if (itemstack.hasCustomHoverName()) {
            mobentity.setCustomName(itemstack.getHoverName());
        }
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
    }

    private static Optional<Mob> makeBabyMob(ServerLevel level, EntityType<? extends Mob> entityType, Entity parent, MobSpawnType spawnReason) {
        Mob mobentity;
        if (parent instanceof AgeableMob ageableMob) {
            mobentity = ageableMob.getBreedOffspring(level, ageableMob);
        } else {
            mobentity = entityType.create(level);
        }
        if (mobentity == null) {
            return Optional.empty();
        }
        if (!mobentity.isBaby()) {
            throw new RuntimeException("baby mob must be a baby by default");
        }
        mobentity.moveTo(parent.getX(), parent.getY(), parent.getZ(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
        level.addFreshEntityWithPassengers(mobentity);
        mobentity.yHeadRot = mobentity.getYRot();
        mobentity.yBodyRot = mobentity.getYRot();
        mobentity.finalizeSpawn(level, level.getCurrentDifficultyAt(mobentity.blockPosition()), spawnReason, null, null);
        return Optional.of(mobentity);
    }
}
