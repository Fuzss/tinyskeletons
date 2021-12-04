package fuzs.tinyskeletons.registry;

import fuzs.puzzleslib.registry.RegistryManager;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.world.entity.monster.BabySkeleton;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeleton;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(TinySkeletons.MOD_ID);
    public static final RegistryObject<EntityType<BabySkeleton>> BABY_SKELETON_ENTITY_TYPE = REGISTRY.registerRawEntityType("baby_skeleton", () -> EntityType.Builder.of(BabySkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<BabyWitherSkeleton>> BABY_WITHER_SKELETON_ENTITY_TYPE = REGISTRY.registerRawEntityType("baby_wither_skeleton", () -> EntityType.Builder.of(BabyWitherSkeleton::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 2.4F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<BabyStray>> BABY_STRAY_ENTITY_TYPE = REGISTRY.registerRawEntityType("baby_stray", () -> EntityType.Builder.of(BabyStray::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));

    public static void touch() {

    }
}
