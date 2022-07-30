package fuzs.tinyskeletons.init;

import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.world.entity.monster.BabySkeleton;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeleton;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(TinySkeletons.MOD_ID);
    public static final RegistryReference<EntityType<BabySkeleton>> BABY_SKELETON_ENTITY_TYPE = REGISTRY.registerEntityTypeBuilder("baby_skeleton", () -> EntityType.Builder.of(BabySkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryReference<EntityType<BabyWitherSkeleton>> BABY_WITHER_SKELETON_ENTITY_TYPE = REGISTRY.registerEntityTypeBuilder("baby_wither_skeleton", () -> EntityType.Builder.of(BabyWitherSkeleton::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 2.4F).clientTrackingRange(8));
    public static final RegistryReference<EntityType<BabyStray>> BABY_STRAY_ENTITY_TYPE = REGISTRY.registerEntityTypeBuilder("baby_stray", () -> EntityType.Builder.of(BabyStray::new, MobCategory.MONSTER).sized(0.6F, 1.99F).immuneTo(Blocks.POWDER_SNOW).clientTrackingRange(8));

    public static void touch() {

    }
}
