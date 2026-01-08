package fuzs.tinyskeletons.init;

import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.world.entity.monster.projectile.throwableitemprojectile.HurtingItemProjectile;
import fuzs.tinyskeletons.world.entity.monster.skeleton.*;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(TinySkeletons.MOD_ID);
    public static final Holder.Reference<EntityType<BabySkeleton>> BABY_SKELETON_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "baby_skeleton",
            () -> EntityType.Builder.of(BabySkeleton::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<BabyWitherSkeleton>> BABY_WITHER_SKELETON_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "baby_wither_skeleton",
            () -> EntityType.Builder.of(BabyWitherSkeleton::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.7F, 2.4F)
                    .eyeHeight(2.1F)
                    .ridingOffset(-0.875F)
                    .clientTrackingRange(8)
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<BabyStray>> BABY_STRAY_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "baby_stray",
            () -> EntityType.Builder.of(BabyStray::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .immuneTo(Blocks.POWDER_SNOW)
                    .clientTrackingRange(8)
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<BabyBogged>> BABY_BOGGED_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "baby_bogged",
            () -> EntityType.Builder.of(BabyBogged::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<BabyParched>> BABY_PARCHED_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "baby_parched",
            () -> EntityType.Builder.of(BabyParched::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<HurtingItemProjectile>> THROWN_ITEM_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "thrown_item",
            () -> EntityType.Builder.<HurtingItemProjectile>of(HurtingItemProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10));

    static final TagFactory TAGS = TagFactory.make(TinySkeletons.MOD_ID);
    public static final TagKey<Item> BABY_BOGGED_THROWABLES_ITEM_TAG = TAGS.registerItemTag("baby_bogged_throwables");
    public static final TagKey<Item> BABY_PARCHED_THROWABLES_ITEM_TAG = TAGS.registerItemTag("baby_parched_throwables");
    public static final TagKey<Item> BABY_STRAY_THROWABLES_ITEM_TAG = TAGS.registerItemTag("baby_stray_throwables");
    public static final TagKey<EntityType<?>> SKELETONS_ENTITY_TAG = TAGS.registerEntityTypeTag("skeletons");

    public static void bootstrap() {
        // NO-OP
    }
}
