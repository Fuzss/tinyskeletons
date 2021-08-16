package fuzs.puzzlesworkshop.element;

import fuzs.puzzleslib.PuzzlesLib;
import fuzs.puzzleslib.element.extension.ClientExtensibleElement;
import fuzs.puzzlesworkshop.PuzzlesWorkshop;
import fuzs.puzzlesworkshop.client.element.TinySkeletonsExtension;
import fuzs.puzzlesworkshop.entity.monster.BabySkeletonEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Optional;

public class TinySkeletonsElement extends ClientExtensibleElement<TinySkeletonsExtension> {

    @ObjectHolder(PuzzlesWorkshop.MODID + ":" + "baby_skeleton")
    public static final EntityType<BabySkeletonEntity> BABY_SKELETON_ENTITY = null;

    public TinySkeletonsElement() {
        super(element -> new TinySkeletonsExtension((TinySkeletonsElement) element));
    }

    @Override
    public String[] getDescription() {
        return new String[] {"SKELTONS"};
    }



    @SuppressWarnings("ConstantConditions")
    @Override
    public void constructCommon() {

        this.addListener(this::onSpecialSpawn);
        this.addListener(this::onInteract);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEntityAttributeCreation);
        PuzzlesLib.getRegistryManager().registerEntityType("baby_skeleton", () -> EntityType.Builder.<SkeletonEntity>of(BabySkeletonEntity::new, EntityClassification.MONSTER).sized(0.3F, 0.995F).clientTrackingRange(8).build("baby_skeleton"));
    }

    @Override
    public void setupCommon2() {
        EntitySpawnPlacementRegistry.register(BABY_SKELETON_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
    }

    public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(BABY_SKELETON_ENTITY, MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375D).build());
    }

    public void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn evt) {

        Entity target = evt.getEntity();
        if (target instanceof SkeletonEntity && ZombieEntity.getSpawnAsBabyOdds(evt.getWorld().getRandom())) {

            if (evt.getWorld() instanceof ServerWorld) {

//                ((SkeletonEntity) evt.getEntity()).convertTo(BABY_SKELETON_ENTITY, true);
                ServerWorld world = (ServerWorld) evt.getWorld();
                MobEntity mobentity = BABY_SKELETON_ENTITY.create(world);
                if (mobentity != null) {

                    mobentity.moveTo(target.getX(), target.getY(), target.getZ(), MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
                    world.addFreshEntityWithPassengers(mobentity);
                    mobentity.yHeadRot = mobentity.yRot;
                    mobentity.yBodyRot = mobentity.yRot;
                    mobentity.finalizeSpawn(world, world.getCurrentDifficultyAt(mobentity.blockPosition()), evt.getSpawnReason(), null, null);

                    evt.setCanceled(true);
                }
            }
        }
    }


    public void onInteract(PlayerInteractEvent.EntityInteract evt) {

        if (evt.getTarget() instanceof SkeletonEntity) {

            SkeletonEntity target = (SkeletonEntity) evt.getTarget();
            if (target.isAlive() && evt.getItemStack().getItem() instanceof SpawnEggItem) {

                if (evt.getWorld() instanceof ServerWorld) {
                    ItemStack itemstack = evt.getItemStack();
                    SpawnEggItem spawneggitem = (SpawnEggItem) itemstack.getItem();
                    EntityType<?> type = spawneggitem.getType(itemstack.getTag());
                    if (type.equals(EntityType.SKELETON)) {

//                        Entity baby = BABY_SKELETON_ENTITY.spawn((ServerWorld) evt.getWorld(), itemstack, evt.getPlayer(), target.blockPosition(), SpawnReason.SPAWN_EGG, false, false);
//                        if (baby != null) {
//
//                            baby.moveTo(target.getX(), target.getY(), target.getZ(), baby.yRot, baby.xRot);
//                            if (!evt.getPlayer().abilities.instabuild) {
//                                itemstack.shrink(1);
//                            }
//                            evt.getPlayer().awardStat(Stats.ITEM_USED.get(spawneggitem));
//                            evt.setCancellationResult(ActionResultType.SUCCESS);
//                        }

                        ServerWorld world = (ServerWorld) evt.getWorld();
                        MobEntity mobentity = BABY_SKELETON_ENTITY.create(world);
                        if (mobentity != null) {

                            mobentity.moveTo(target.getX(), target.getY(), target.getZ(), MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
                            world.addFreshEntityWithPassengers(mobentity);
                            mobentity.yHeadRot = mobentity.yRot;
                            mobentity.yBodyRot = mobentity.yRot;
                            mobentity.finalizeSpawn(world, world.getCurrentDifficultyAt(mobentity.blockPosition()), SpawnReason.SPAWN_EGG, null, null);
                            mobentity.playAmbientSound();

                            if (itemstack.hasCustomHoverName()) {
                                mobentity.setCustomName(itemstack.getHoverName());
                            }

                            if (!evt.getPlayer().abilities.instabuild) {
                                itemstack.shrink(1);
                            }
                            evt.getPlayer().awardStat(Stats.ITEM_USED.get(spawneggitem));
                            evt.setCancellationResult(ActionResultType.SUCCESS);
                        }

//                        Optional<MobEntity> optional = this.spawnOffspringFromSpawnEgg(evt.getPlayer(), target, BABY_SKELETON_ENTITY, (ServerWorld) evt.getWorld(), target.position(), itemStack);
//                        optional.ifPresent(mobEntity -> evt.setCancellationResult(ActionResultType.SUCCESS));
                    }
                } else {
                    evt.setCancellationResult(ActionResultType.CONSUME);
                }
            }
        }
    }

    public Optional<MobEntity> spawnOffspringFromSpawnEgg(PlayerEntity p_234809_1_, MobEntity p_234809_2_, EntityType<? extends MobEntity> entitytype, ServerWorld world, Vector3d p_234809_5_, ItemStack itemstack) {

        MobEntity mobentity = entitytype.create(world);
        if (mobentity == null) {
            return Optional.empty();
        } else {
            mobentity.setBaby(true);
            if (!mobentity.isBaby()) {
                return Optional.empty();
            } else {
                mobentity.moveTo(p_234809_5_.x(), p_234809_5_.y(), p_234809_5_.z(), 0.0F, 0.0F);
                world.addFreshEntityWithPassengers(mobentity);
                if (itemstack.hasCustomHoverName()) {
                    mobentity.setCustomName(itemstack.getHoverName());
                }

                if (!p_234809_1_.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                return Optional.of(mobentity);
            }
        }
    }


}
