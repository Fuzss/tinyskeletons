package fuzs.puzzlesworkshop.element;

import com.google.common.collect.Maps;
import fuzs.puzzleslib.PuzzlesLib;
import fuzs.puzzleslib.element.extension.ClientExtensibleElement;
import fuzs.puzzlesworkshop.PuzzlesWorkshop;
import fuzs.puzzlesworkshop.client.element.TinySkeletonsExtension;
import fuzs.puzzlesworkshop.entity.monster.BabySkeletonEntity;
import fuzs.puzzlesworkshop.entity.monster.BabyWitherSkeletonEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Map;
import java.util.Optional;

public class TinySkeletonsElement extends ClientExtensibleElement<TinySkeletonsExtension> {

    @ObjectHolder(PuzzlesWorkshop.MODID + ":" + "baby_skeleton")
    public static final EntityType<BabySkeletonEntity> BABY_SKELETON_ENTITY = null;
    @ObjectHolder(PuzzlesWorkshop.MODID + ":" + "baby_wither_skeleton")
    public static final EntityType<BabyWitherSkeletonEntity> BABY_WITHER_SKELETON_ENTITY = null;

    private final Map<EntityType<? extends MobEntity>, EntityType<? extends MobEntity>> babyMobConversions = Maps.newHashMap();

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
        this.addListener(this::onSpecialSpawn);
        this.addListener(this::onEntityInteract);
        PuzzlesLib.getRegistryManager().registerEntityType("baby_skeleton", () -> EntityType.Builder.of(BabySkeletonEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build("baby_skeleton"));
        PuzzlesLib.getRegistryManager().registerEntityType("baby_wither_skeleton", () -> EntityType.Builder.of(BabyWitherSkeletonEntity::new, EntityClassification.MONSTER).fireImmune().sized(0.7F, 2.4F).clientTrackingRange(8).build("baby_wither_skeleton"));
    }

    @Override
    public void setupCommon2() {

        this.babyMobConversions.put(EntityType.SKELETON, BABY_SKELETON_ENTITY);
        this.babyMobConversions.put(EntityType.WITHER_SKELETON, BABY_WITHER_SKELETON_ENTITY);
        EntitySpawnPlacementRegistry.register(BABY_SKELETON_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(BABY_WITHER_SKELETON_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
    }

    private void onEntityAttributeCreation(final EntityAttributeCreationEvent evt) {

        evt.put(BABY_SKELETON_ENTITY, MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
        evt.put(BABY_WITHER_SKELETON_ENTITY, MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
    }

    private Optional<MobEntity> createBabyMob(ServerWorld world, EntityType<? extends MobEntity> type, Entity parent, SpawnReason spawnReason) {

        MobEntity mobentity = type.create(world);
        if (mobentity != null) {

            mobentity.moveTo(parent.getX(), parent.getY(), parent.getZ(), MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
            world.addFreshEntityWithPassengers(mobentity);
            mobentity.yHeadRot = mobentity.yRot;
            mobentity.yBodyRot = mobentity.yRot;
            mobentity.finalizeSpawn(world, world.getCurrentDifficultyAt(mobentity.blockPosition()), spawnReason, null, null);
            return Optional.of(mobentity);
        }

        return Optional.empty();
    }

    private void onSpecialSpawn(final LivingSpawnEvent.SpecialSpawn evt) {

        if (evt.getWorld() instanceof ServerWorld && ZombieEntity.getSpawnAsBabyOdds(evt.getWorld().getRandom())) {

            EntityType<? extends MobEntity> babyType = this.babyMobConversions.get(evt.getEntity().getType());
            if (babyType != null) {

                this.createBabyMob((ServerWorld) evt.getWorld(), babyType, evt.getEntity(), evt.getSpawnReason())
                        .ifPresent(mobentity -> evt.setCanceled(true));
            }
        }
    }


    private void onEntityInteract(final PlayerInteractEvent.EntityInteract evt) {

        if (evt.getTarget().isAlive() && evt.getItemStack().getItem() instanceof SpawnEggItem) {

            if (evt.getWorld() instanceof ServerWorld) {

                ItemStack itemstack = evt.getItemStack();
                EntityType<?> eggType = ((SpawnEggItem) itemstack.getItem()).getType(itemstack.getTag());
                EntityType<? extends MobEntity> babyType = this.babyMobConversions.get(eggType);
                if (babyType != null) {

                    Optional<MobEntity> babyMob = this.createBabyMob((ServerWorld) evt.getWorld(), babyType, evt.getTarget(), SpawnReason.SPAWN_EGG);
                    babyMob.ifPresent(mobentity -> {

                        mobentity.playAmbientSound();
                        if (itemstack.hasCustomHoverName()) {

                            mobentity.setCustomName(itemstack.getHoverName());
                        }

                        if (!evt.getPlayer().abilities.instabuild) {

                            itemstack.shrink(1);
                        }

                        evt.getPlayer().awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                        evt.setCancellationResult(ActionResultType.SUCCESS);
                    });
                }
            } else {

                evt.setCancellationResult(ActionResultType.CONSUME);
            }
        }
    }

}
