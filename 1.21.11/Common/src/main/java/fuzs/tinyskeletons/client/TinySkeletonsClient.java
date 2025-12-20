package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.core.v1.context.PackRepositorySourcesContext;
import fuzs.puzzleslib.api.resources.v1.PackResourcesHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModModelLayers;
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
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.entity.ArmorModelSet;
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
        Supplier<LayerDefinition> skeletonLayer = () -> {
            return SkeletonModel.createBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        };
        ArmorModelSet<LayerDefinition> armorModelSet = HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32).apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerLayerDefinition(ModModelLayers.BABY_SKELETON, skeletonLayer);
        registerLayerDefinitions(context, ModModelLayers.BABY_SKELETON_ARMOR, armorModelSet);
        context.registerLayerDefinition(ModModelLayers.BABY_STRAY, skeletonLayer);
        registerLayerDefinitions(context, ModModelLayers.BABY_STRAY_ARMOR, armorModelSet);
        context.registerLayerDefinition(ModModelLayers.BABY_STRAY_OUTER_LAYER, () -> {
            return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32)
                    .apply(HumanoidModel.BABY_TRANSFORMER);
        });
        MeshTransformer witherSkeletonTransformer = MeshTransformer.scaling(1.2F);
        context.registerLayerDefinition(ModModelLayers.BABY_WITHER_SKELETON, () -> {
            return skeletonLayer.get().apply(witherSkeletonTransformer);
        });
        registerLayerDefinitions(context,
                ModModelLayers.BABY_WITHER_SKELETON_ARMOR,
                armorModelSet.map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(witherSkeletonTransformer);
                }));
        context.registerLayerDefinition(ModModelLayers.BABY_BOGGED, () -> {
            return BoggedModel.createBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        });
        registerLayerDefinitions(context, ModModelLayers.BABY_BOGGED_ARMOR, armorModelSet);
        context.registerLayerDefinition(ModModelLayers.BABY_BOGGED_OUTER_LAYER, () -> {
            return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.2F), 0.0F), 64, 32)
                    .apply(HumanoidModel.BABY_TRANSFORMER);
        });
    }

    @Deprecated(forRemoval = true)
    private static void registerLayerDefinitions(LayerDefinitionsContext context, ArmorModelSet<ModelLayerLocation> modelLayerSet, ArmorModelSet<LayerDefinition> layerDefinitionSet) {
        context.registerLayerDefinition(modelLayerSet.head(), layerDefinitionSet::head);
        context.registerLayerDefinition(modelLayerSet.chest(), layerDefinitionSet::chest);
        context.registerLayerDefinition(modelLayerSet.legs(), layerDefinitionSet::legs);
        context.registerLayerDefinition(modelLayerSet.feet(), layerDefinitionSet::feet);
    }

    @Override
    public void onAddResourcePackFinders(PackRepositorySourcesContext context) {
        context.registerRepositorySource(PackResourcesHelper.buildClientPack(TinySkeletons.id(
                "dynamically_copied_skeleton_textures"), BabySkeletonPackResources::new, false));
    }
}
