package fuzs.tinyskeletons.registry;

import fuzs.puzzleslib.registry.RegistryManager;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.world.entity.monster.BabySkeletonEntity;
import fuzs.tinyskeletons.world.entity.monster.BabyStrayEntity;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeletonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(TinySkeletons.MOD_ID);
    public static final RegistryObject<EntityType<BabySkeletonEntity>> BABY_SKELETON_ENTITY_TYPE = REGISTRY.registerRawEntityType("baby_skeleton", () -> EntityType.Builder.of(BabySkeletonEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<BabyWitherSkeletonEntity>> BABY_WITHER_SKELETON_ENTITY_TYPE = REGISTRY.registerRawEntityType("baby_wither_skeleton", () -> EntityType.Builder.of(BabyWitherSkeletonEntity::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 2.4F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<BabyStrayEntity>> BABY_STRAY_ENTITY_TYPE = REGISTRY.registerRawEntityType("baby_stray", () -> EntityType.Builder.of(BabyStrayEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));

    public static void touch() {

    }
}
