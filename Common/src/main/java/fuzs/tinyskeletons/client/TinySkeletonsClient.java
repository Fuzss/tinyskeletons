package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.core.v1.context.PackRepositorySourcesContext;
import fuzs.puzzleslib.api.resources.v1.PackResourcesHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.packs.BabySkeletonPackResources;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class TinySkeletonsClient implements ClientModConstructor {

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), BabySkeletonRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), BabyWitherSkeletonRenderer::new);
        context.registerEntityRenderer(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), StrayRenderer::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        Supplier<LayerDefinition> skeletonLayer = SkeletonModel::createBodyLayer;
        Supplier<LayerDefinition> innerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0.0F), 64, 32);
        Supplier<LayerDefinition> outerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32);
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

    @Override
    public void onAddResourcePackFinders(PackRepositorySourcesContext context) {
        context.addRepositorySources(PackResourcesHelper.buildClientPack(BabySkeletonPackResources::new, TinySkeletons.MOD_ID, Component.literal(TinySkeletons.MOD_NAME), Component.literal("Teeny, tiny skeletons, send shivers down your spine..."), true, false));
    }
}
