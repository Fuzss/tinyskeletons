package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.core.v1.context.PackRepositorySourcesContext;
import fuzs.puzzleslib.api.resources.v1.PackResourcesHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.model.geom.ModModelLayers;
import fuzs.tinyskeletons.client.packs.SkeletonTextureCopiesPackResources;
import fuzs.tinyskeletons.client.renderer.entity.*;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.monster.skeleton.BoggedModel;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
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
        context.registerEntityRenderer(ModRegistry.BABY_PARCHED_ENTITY_TYPE.value(), BabyParchedRenderer::new);
        context.registerEntityRenderer(ModRegistry.THROWN_ITEM_ENTITY_TYPE.value(), ThrownItemRenderer::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModModelLayers.BABY_SKELETON, () -> {
            return SkeletonModel.createBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerArmorDefinition(ModModelLayers.BABY_SKELETON_ARMOR,
                () -> HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
                    return LayerDefinition.create(meshDefinition, 64, 32).apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerLayerDefinition(ModModelLayers.BABY_STRAY, () -> {
            return SkeletonModel.createBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerArmorDefinition(ModModelLayers.BABY_STRAY_ARMOR,
                () -> HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
                    return LayerDefinition.create(meshDefinition, 64, 32).apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerLayerDefinition(ModModelLayers.BABY_STRAY_OUTER_LAYER, () -> {
            return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32)
                    .apply(HumanoidModel.BABY_TRANSFORMER);
        });
        MeshTransformer witherSkeletonTransformer = MeshTransformer.scaling(1.2F);
        context.registerLayerDefinition(ModModelLayers.BABY_WITHER_SKELETON, () -> {
            return ((Supplier<LayerDefinition>) () -> {
                return SkeletonModel.createBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
            }).get().apply(witherSkeletonTransformer);
        });
        context.registerArmorDefinition(ModModelLayers.BABY_WITHER_SKELETON_ARMOR,
                () -> HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
                    return LayerDefinition.create(meshDefinition, 64, 32).apply(HumanoidModel.BABY_TRANSFORMER);
                }).map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(witherSkeletonTransformer);
                }));
        context.registerLayerDefinition(ModModelLayers.BABY_BOGGED, () -> {
            return BoggedModel.createBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerArmorDefinition(ModModelLayers.BABY_BOGGED_ARMOR,
                () -> HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
                    return LayerDefinition.create(meshDefinition, 64, 32).apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerLayerDefinition(ModModelLayers.BABY_BOGGED_OUTER_LAYER, () -> {
            return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.2F), 0.0F), 64, 32)
                    .apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerLayerDefinition(ModModelLayers.BABY_PARCHED, () -> {
            return SkeletonModel.createSingleModelDualBodyLayer().apply(HumanoidModel.BABY_TRANSFORMER);
        });
        context.registerArmorDefinition(ModModelLayers.BABY_PARCHED_ARMOR,
                () -> HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
                    return LayerDefinition.create(meshDefinition, 64, 32).apply(HumanoidModel.BABY_TRANSFORMER);
                }));
    }

    @Override
    public void onAddResourcePackFinders(PackRepositorySourcesContext context) {
        context.registerRepositorySource(PackResourcesHelper.buildClientPack(TinySkeletons.id("skeleton_texture_copies"),
                SkeletonTextureCopiesPackResources::new,
                true));
    }
}
