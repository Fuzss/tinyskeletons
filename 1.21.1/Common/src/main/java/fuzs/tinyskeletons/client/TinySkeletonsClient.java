package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.core.v1.context.PackRepositorySourcesContext;
import fuzs.puzzleslib.api.resources.v1.PackResourcesHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.client.renderer.entity.BabyBoggedRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyStrayRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.client.model.BoggedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import java.util.function.Supplier;

public class TinySkeletonsClient implements ClientModConstructor {

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(), BabySkeletonRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
                BabyWitherSkeletonRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_STRAY_ENTITY_TYPE.value(), BabyStrayRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_BOGGED_ENTITY_TYPE.value(), BabyBoggedRenderer::new);
        context.registerEntityRenderer(ModRegistry.MUSHROOM_ENTITY_TYPE.value(), ThrownItemRenderer::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        Supplier<LayerDefinition> skeletonLayer = SkeletonModel::createBodyLayer;
        Supplier<LayerDefinition> innerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(
                LayerDefinitions.INNER_ARMOR_DEFORMATION,
                0.0F), 64, 32);
        Supplier<LayerDefinition> outerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(
                LayerDefinitions.OUTER_ARMOR_DEFORMATION,
                0.0F), 64, 32);
        Supplier<LayerDefinition> strayOuterLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(
                0.25F), 0.0F), 64, 32);
        Supplier<LayerDefinition> boggedOuterLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(
                0.2F), 0.0F), 64, 32);
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
        context.registerLayerDefinition(ModClientRegistry.BABY_BOGGED, BoggedModel::createBodyLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_BOGGED_INNER_ARMOR, innerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_BOGGED_OUTER_ARMOR, outerArmorLayer);
        context.registerLayerDefinition(ModClientRegistry.BABY_BOGGED_OUTER_LAYER, boggedOuterLayer);
    }

    @Override
    public void onAddResourcePackFinders(PackRepositorySourcesContext context) {
        context.addRepositorySource(PackResourcesHelper.buildClientPack(TinySkeletons.id(
                "dynamically_copied_skeleton_textures"), BabySkeletonPackResources::new, false));
    }
}
