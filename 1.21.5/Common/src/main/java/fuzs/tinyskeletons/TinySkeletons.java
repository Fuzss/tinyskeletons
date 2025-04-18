package fuzs.tinyskeletons;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import fuzs.tinyskeletons.init.ModRegistry;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TinySkeletons implements ModConstructor {
    public static final String MOD_ID = "tinyskeletons";
    public static final String MOD_NAME = "Tiny Skeletons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
    }

    @Override
    public void onCommonSetup() {
        registerEventHandlers();
        BabyConversionHandler.registerConversion(EntityType.SKELETON, ModRegistry.BABY_SKELETON_ENTITY_TYPE.value());
        BabyConversionHandler.registerConversion(EntityType.WITHER_SKELETON,
                ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value());
        BabyConversionHandler.registerConversion(EntityType.STRAY, ModRegistry.BABY_STRAY_ENTITY_TYPE.value());
        BabyConversionHandler.registerConversion(EntityType.BOGGED, ModRegistry.BABY_BOGGED_ENTITY_TYPE.value());
    }

    private static void registerEventHandlers() {
        // I guess we're using this event now, the other one wouldn't show the hand swing on Fabric
        PlayerInteractEvents.USE_ENTITY_AT.register(BabyConversionHandler::onEntityInteract);
        ServerEntityLevelEvents.SPAWN.register(BabyConversionHandler::onEntitySpawn);
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.BABY_STRAY_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BabyStray::checkBabyStraySpawnRules);
        context.registerSpawnPlacement(ModRegistry.BABY_BOGGED_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
    }

    @Override
    public void onRegisterEntityAttributes(EntityAttributesContext context) {
        context.registerAttributes(ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.ATTACK_DAMAGE, 1.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.3));
        context.registerAttributes(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.ATTACK_DAMAGE, 1.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.3));
        context.registerAttributes(ModRegistry.BABY_STRAY_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.ATTACK_DAMAGE, 1.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.3));
        context.registerAttributes(ModRegistry.BABY_BOGGED_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.ATTACK_DAMAGE, 1.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.3));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
