package fuzs.tinyskeletons.client.model.geom;

import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.tinyskeletons.TinySkeletons;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ArmorModelSet;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(TinySkeletons.MOD_ID);
    public static final ModelLayerLocation BABY_SKELETON = MODEL_LAYERS.registerModelLayer("baby_skeleton");
    public static final ArmorModelSet<ModelLayerLocation> BABY_SKELETON_ARMOR = MODEL_LAYERS.registerArmorSet(
            "baby_skeleton");
    public static final ModelLayerLocation BABY_STRAY = MODEL_LAYERS.registerModelLayer("baby_stray");
    public static final ArmorModelSet<ModelLayerLocation> BABY_STRAY_ARMOR = MODEL_LAYERS.registerArmorSet("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_LAYER = MODEL_LAYERS.registerModelLayer("baby_stray",
            "outer");
    public static final ModelLayerLocation BABY_WITHER_SKELETON = MODEL_LAYERS.registerModelLayer("baby_wither_skeleton");
    public static final ArmorModelSet<ModelLayerLocation> BABY_WITHER_SKELETON_ARMOR = MODEL_LAYERS.registerArmorSet(
            "baby_wither_skeleton");
    public static final ModelLayerLocation BABY_BOGGED = MODEL_LAYERS.registerModelLayer("baby_bogged");
    public static final ArmorModelSet<ModelLayerLocation> BABY_BOGGED_ARMOR = MODEL_LAYERS.registerArmorSet(
            "baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_OUTER_LAYER = MODEL_LAYERS.registerModelLayer("baby_bogged",
            "outer");
    public static final ModelLayerLocation BABY_PARCHED = MODEL_LAYERS.registerModelLayer("baby_parched");
    public static final ArmorModelSet<ModelLayerLocation> BABY_PARCHED_ARMOR = MODEL_LAYERS.registerArmorSet(
            "baby_parched");
}
