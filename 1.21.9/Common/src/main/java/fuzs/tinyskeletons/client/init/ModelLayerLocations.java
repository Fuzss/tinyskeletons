package fuzs.tinyskeletons.client.init;

import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.tinyskeletons.TinySkeletons;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModelLayerLocations {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(TinySkeletons.MOD_ID);
    public static final ModelLayerLocation BABY_SKELETON = MODEL_LAYERS.registerModelLayer("baby_skeleton");
    public static final ModelLayerLocation BABY_SKELETON_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "baby_skeleton");
    public static final ModelLayerLocation BABY_SKELETON_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "baby_skeleton");
    public static final ModelLayerLocation BABY_STRAY = MODEL_LAYERS.registerModelLayer("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_LAYER = MODEL_LAYERS.registerModelLayer("baby_stray",
            "outer");
    public static final ModelLayerLocation BABY_WITHER_SKELETON = MODEL_LAYERS.registerModelLayer("baby_wither_skeleton");
    public static final ModelLayerLocation BABY_WITHER_SKELETON_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "baby_wither_skeleton");
    public static final ModelLayerLocation BABY_WITHER_SKELETON_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "baby_wither_skeleton");
    public static final ModelLayerLocation BABY_BOGGED = MODEL_LAYERS.registerModelLayer("baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_OUTER_LAYER = MODEL_LAYERS.registerModelLayer("baby_bogged",
            "outer");
}
