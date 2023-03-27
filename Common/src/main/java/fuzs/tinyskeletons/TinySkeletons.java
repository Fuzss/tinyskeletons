package fuzs.tinyskeletons;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.ModLifecycleContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import fuzs.tinyskeletons.init.ModRegistry;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
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
    public void onCommonSetup(ModLifecycleContext context) {
        BabyConversionHandler.registerConversion(EntityType.SKELETON, ModRegistry.BABY_SKELETON_ENTITY_TYPE.get());
        BabyConversionHandler.registerConversion(EntityType.WITHER_SKELETON, ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get());
        BabyConversionHandler.registerConversion(EntityType.STRAY, ModRegistry.BABY_STRAY_ENTITY_TYPE.get());
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BabyStray::checkBabyStraySpawnRules);
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3));
        context.registerEntityAttributes(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3));
        context.registerEntityAttributes(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.3));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
