package fuzs.tinyskeletons.element;

import com.google.common.collect.Maps;
import fuzs.puzzleslib.PuzzlesLib;
import fuzs.puzzleslib.element.extension.ClientExtensibleElement;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.element.TinySkeletonsExtension;
import fuzs.tinyskeletons.entity.monster.BabySkeletonEntity;
import fuzs.tinyskeletons.entity.monster.BabyStrayEntity;
import fuzs.tinyskeletons.entity.monster.BabyWitherSkeletonEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Map;
import java.util.Optional;

public class TinySkeletonsElement extends ClientExtensibleElement<TinySkeletonsExtension> {
    @ObjectHolder(TinySkeletons.MODID + ":" + "baby_skeleton")
    public static final EntityType<BabySkeletonEntity> BABY_SKELETON_ENTITY = null;
    @ObjectHolder(TinySkeletons.MODID + ":" + "baby_wither_skeleton")
    public static final EntityType<BabyWitherSkeletonEntity> BABY_WITHER_SKELETON_ENTITY = null;
    @ObjectHolder(TinySkeletons.MODID + ":" + "baby_stray")
    public static final EntityType<BabyStrayEntity> BABY_STRAY_ENTITY = null;

    private static final Map<EntityType<? extends MobEntity>, EntityType<? extends MobEntity>> BABY_MOB_CONVERSIONS = Maps.newHashMap();

    public TinySkeletonsElement() {
        super(element -> new TinySkeletonsExtension((TinySkeletonsElement) element));
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Teeny, tiny skeletons, send shivers down your spine..."};
    }

    @Override
    public void constructCommon() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEntityAttributeCreation);
        this.addListener(this::onLivingPackSize);
        this.addListener(this::onSpecialSpawn);
        this.addListener(this::onEntityInteract);
        PuzzlesLib.getRegistryManagerV2().registerRawEntityType("baby_skeleton", () -> EntityType.Builder.of(BabySkeletonEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
        PuzzlesLib.getRegistryManagerV2().registerRawEntityType("baby_wither_skeleton", () -> EntityType.Builder.of(BabyWitherSkeletonEntity::new, EntityClassification.MONSTER).fireImmune().sized(0.7F, 2.4F).clientTrackingRange(8));
        PuzzlesLib.getRegistryManagerV2().registerRawEntityType("baby_stray", () -> EntityType.Builder.of(BabyStrayEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
    }

    @Override
    public void setupCommon2() {
        BABY_MOB_CONVERSIONS.put(EntityType.SKELETON, BABY_SKELETON_ENTITY);
        BABY_MOB_CONVERSIONS.put(EntityType.WITHER_SKELETON, BABY_WITHER_SKELETON_ENTITY);
        BABY_MOB_CONVERSIONS.put(EntityType.STRAY, BABY_STRAY_ENTITY);
        EntitySpawnPlacementRegistry.register(BABY_SKELETON_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(BABY_WITHER_SKELETON_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(BABY_STRAY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BabyStrayEntity::checkBabyStraySpawnRules);
    }

    private void onEntityAttributeCreation(final EntityAttributeCreationEvent evt) {
        evt.put(BABY_SKELETON_ENTITY, MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
        evt.put(BABY_WITHER_SKELETON_ENTITY, MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
        evt.put(BABY_STRAY_ENTITY, MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
    }

    public void onLivingPackSize(final LivingPackSizeEvent evt) {
        // we hijack this event for replacing naturally spawned adult mobs
        // there are two other events fired before this, but only this one works as the entity is already added to the world here
        // removing the entity when not added to a world yet will log a warning every time, might also have worse side effects
        if (evt.getEntity().level instanceof ServerWorld && ZombieEntity.getSpawnAsBabyOdds(evt.getEntity().level.getRandom())) {
            EntityType<? extends MobEntity> babyType = BABY_MOB_CONVERSIONS.get(evt.getEntity().getType());
            if (babyType != null) {
                makeBabyMob((ServerWorld) evt.getEntity().level, babyType, evt.getEntity(), SpawnReason.NATURAL).ifPresent(mobentity -> evt.getEntity().remove());
            }
        }
    }

    public void onSpecialSpawn(final LivingSpawnEvent.SpecialSpawn evt) {
        // only respond to event firing from EntityType::spawn
        // the event is fired at two more places, but those are bugged and don't prevent the entity from spawning when canceled
        // they only prevent any equipment from being added for some reason
        final SpawnReason spawnReason = evt.getSpawnReason();
        if (spawnReason != SpawnReason.NATURAL && spawnReason != SpawnReason.SPAWNER && spawnReason != SpawnReason.COMMAND) {
            if (evt.getWorld() instanceof ServerWorld && ZombieEntity.getSpawnAsBabyOdds(evt.getWorld().getRandom())) {
                EntityType<? extends MobEntity> babyType = BABY_MOB_CONVERSIONS.get(evt.getEntity().getType());
                if (babyType != null) {
                    makeBabyMob((ServerWorld) evt.getWorld(), babyType, evt.getEntity(), spawnReason).ifPresent(mobentity -> evt.setCanceled(true));
                }
            }
        }
    }

    public void onEntityInteract(final PlayerInteractEvent.EntityInteract evt) {
        final Entity target = evt.getTarget();
        if (target.isAlive() && evt.getItemStack().getItem() instanceof SpawnEggItem) {
            ItemStack itemstack = evt.getItemStack();
            EntityType<?> eggType = ((SpawnEggItem) itemstack.getItem()).getType(itemstack.getTag());
            EntityType<? extends MobEntity> babyType = BABY_MOB_CONVERSIONS.get(eggType);
            if (babyType != null && (target.getType() == babyType || target.getType() == eggType)) {
                evt.setCanceled(true);
                if (evt.getWorld() instanceof ServerWorld) {
                    final Optional<MobEntity> mob = makeBabyMob((ServerWorld) evt.getWorld(), babyType, target, SpawnReason.SPAWN_EGG);
                    if (mob.isPresent()) {
                        this.finalizeSpawnEggMob(mob.get(), itemstack, evt.getPlayer());
                        evt.setCancellationResult(ActionResultType.SUCCESS);
                        return;
                    }
                    evt.setCancellationResult(ActionResultType.PASS);
                } else {
                    evt.setCancellationResult(ActionResultType.CONSUME);
                }
            }
        }
    }

    private void finalizeSpawnEggMob(MobEntity mobentity, ItemStack itemstack, PlayerEntity player) {
        mobentity.playAmbientSound();
        if (itemstack.hasCustomHoverName()) {
            mobentity.setCustomName(itemstack.getHoverName());
        }
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }
        player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
    }

    private static Optional<MobEntity> makeBabyMob(ServerWorld level, EntityType<? extends MobEntity> entityType, Entity parent, SpawnReason spawnReason) {
        MobEntity mobentity;
        if (parent instanceof AgeableEntity) {
            mobentity = ((AgeableEntity) parent).getBreedOffspring(level, (AgeableEntity) parent);
        } else {
            mobentity = entityType.create(level);
        }
        if (mobentity == null) {
            return Optional.empty();
        }
        if (!mobentity.isBaby()) {
            throw new RuntimeException("baby mob must be a baby by default");
        }
        mobentity.moveTo(parent.getX(), parent.getY(), parent.getZ(), MathHelper.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
        mobentity.yHeadRot = mobentity.yRot;
        mobentity.yBodyRot = mobentity.yRot;
        mobentity.finalizeSpawn(level, level.getCurrentDifficultyAt(mobentity.blockPosition()), spawnReason, null, null);
        level.addFreshEntityWithPassengers(mobentity);
        return Optional.of(mobentity);
    }
}
