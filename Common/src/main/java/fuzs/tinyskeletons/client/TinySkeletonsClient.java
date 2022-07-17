package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.client.core.ClientModConstructor;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.StrayRenderer;

import java.util.function.Supplier;

public class TinySkeletonsClient implements ClientModConstructor {
    static final ClientModConstructor INSTANCE = new TinySkeletonsClient();

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), BabySkeletonRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), BabyWitherSkeletonRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), StrayRenderer::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        Supplier<LayerDefinition> skeletonLayer = SkeletonModel::createBodyLayer;
        Supplier<LayerDefinition> innerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5f), 0.0F), 64, 32);
        Supplier<LayerDefinition> outerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.0f), 0.0F), 64, 32);
        Supplier<LayerDefinition> strayOuterLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32);
        context.registerLayerDefinition(ModClientRegistry.BABY_SKELETON, skeletonLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_SKELETON_INNER_ARMOR, innerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_SKELETON_OUTER_ARMOR, outerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_STRAY, skeletonLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_STRAY_INNER_ARMOR, innerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_STRAY_OUTER_ARMOR, outerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_STRAY_OUTER_LAYER, strayOuterLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_WITHER_SKELETON, skeletonLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_WITHER_SKELETON_INNER_ARMOR, innerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_WITHER_SKELETON_OUTER_ARMOR, outerArmorLayer);
    }
}
