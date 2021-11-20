package fuzs.tinyskeletons;

import fuzs.tinyskeletons.world.entity.monster.BabyStrayEntity;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import fuzs.tinyskeletons.registry.ModRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TinySkeletons.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinySkeletons {
    public static final String MOD_ID = "tinyskeletons";
    public static final String MOD_NAME = "Tiny Skeletons";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        final BabyConversionHandler handler = new BabyConversionHandler();
        MinecraftForge.EVENT_BUS.addListener(handler::onSpecialSpawn);
        MinecraftForge.EVENT_BUS.addListener(handler::onEntityInteract);
        ModRegistry.touch();
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent evt) {
        SpawnPlacements.register(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BabyStrayEntity::checkBabyStraySpawnRules);
        BabyConversionHandler.registerConversion(EntityType.SKELETON, ModRegistry.BABY_SKELETON_ENTITY_TYPE.get());
        BabyConversionHandler.registerConversion(EntityType.WITHER_SKELETON, ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get());
        BabyConversionHandler.registerConversion(EntityType.STRAY, ModRegistry.BABY_STRAY_ENTITY_TYPE.get());
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(final EntityAttributeCreationEvent evt) {
        evt.put(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
        evt.put(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
        evt.put(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.375).build());
    }
}
