package fuzs.tinyskeletons.client.init;

import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.tinyskeletons.TinySkeletons;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    static final ModelLayerFactory FACTORY = ModelLayerFactory.from(TinySkeletons.MOD_ID);
    public static final ModelLayerLocation BABY_SKELETON = FACTORY.register("baby_skeleton");
    public static final ModelLayerLocation BABY_SKELETON_INNER_ARMOR = FACTORY.registerInnerArmor("baby_skeleton");
    public static final ModelLayerLocation BABY_SKELETON_OUTER_ARMOR = FACTORY.registerOuterArmor("baby_skeleton");
    public static final ModelLayerLocation BABY_STRAY = FACTORY.register("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_INNER_ARMOR = FACTORY.registerInnerArmor("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_ARMOR = FACTORY.registerOuterArmor("baby_stray");
    public static final ModelLayerLocation BABY_STRAY_OUTER_LAYER = FACTORY.register("baby_stray", "outer");
    public static final ModelLayerLocation BABY_WITHER_SKELETON = FACTORY.register("baby_wither_skeleton");
    public static final ModelLayerLocation BABY_WITHER_SKELETON_INNER_ARMOR = FACTORY.registerInnerArmor(
            "baby_wither_skeleton");
    public static final ModelLayerLocation BABY_WITHER_SKELETON_OUTER_ARMOR = FACTORY.registerOuterArmor(
            "baby_wither_skeleton");
    public static final ModelLayerLocation BABY_BOGGED = FACTORY.register("baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_INNER_ARMOR = FACTORY.registerInnerArmor("baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_OUTER_ARMOR = FACTORY.registerOuterArmor("baby_bogged");
    public static final ModelLayerLocation BABY_BOGGED_OUTER_LAYER = FACTORY.register("baby_bogged", "outer");
}
