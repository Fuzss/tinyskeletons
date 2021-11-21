package fuzs.tinyskeletons.client.registry;

import fuzs.puzzleslib.client.model.geom.ModelLayerRegistry;
import fuzs.tinyskeletons.TinySkeletons;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    private static final ModelLayerRegistry LAYER_REGISTRY = ModelLayerRegistry.of(TinySkeletons.MOD_ID);
    public static final ModelLayerLocation BABY_SKELETON = LAYER_REGISTRY.register("baby_skeleton");
    public static final ModelLayerLocation BABY_SKELETON_INNER_ARMOR = LAYER_REGISTRY.registerInnerArmor("baby_skeleton");
    public static final ModelLayerLocation BABY_SKELETON_OUTER_ARMOR = LAYER_REGISTRY.registerOuterArmor("baby_skeleton");
    public static final ModelLayerLocation BABY_STRAY = LAYER_REGISTRY.register("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_INNER_ARMOR = LAYER_REGISTRY.registerInnerArmor("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_ARMOR = LAYER_REGISTRY.registerOuterArmor("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_LAYER = LAYER_REGISTRY.register("baby_stray", "outer");
    public static final ModelLayerLocation BABY_WITHER_SKELETON = LAYER_REGISTRY.register("baby_wither_skeleton");
    public static final ModelLayerLocation BABY_WITHER_SKELETON_INNER_ARMOR = LAYER_REGISTRY.registerInnerArmor("baby_wither_skeleton");
    public static final ModelLayerLocation BABY_WITHER_SKELETON_OUTER_ARMOR = LAYER_REGISTRY.registerOuterArmor("baby_wither_skeleton");
}
