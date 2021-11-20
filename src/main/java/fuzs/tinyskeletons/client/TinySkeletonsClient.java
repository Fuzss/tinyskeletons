package fuzs.tinyskeletons.client;

import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.registry.ModClientRegistry;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import fuzs.tinyskeletons.registry.ModRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TinySkeletons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TinySkeletonsClient {
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers evt) {
        evt.registerEntityRenderer(ModRegistry.BABY_SKELETON_ENTITY_TYPE.get(), BabySkeletonRenderer::new);
        evt.registerEntityRenderer(ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.get(), BabyWitherSkeletonRenderer::new);
        evt.registerEntityRenderer(ModRegistry.BABY_STRAY_ENTITY_TYPE.get(), StrayRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        Supplier<LayerDefinition> skeletonLayer = SkeletonModel::createBodyLayer;
        Supplier<LayerDefinition> innerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0.0F), 64, 32);
        Supplier<LayerDefinition> outerArmorLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32);
        Supplier<LayerDefinition> strayOuterLayer = () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32);
        evt.registerLayerDefinition(ModClientRegistry.BABY_SKELETON, skeletonLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_SKELETON_INNER_ARMOR, innerArmorLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_SKELETON_OUTER_ARMOR, outerArmorLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_STRAY, skeletonLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_STRAY_INNER_ARMOR, innerArmorLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_STRAY_OUTER_ARMOR, outerArmorLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_STRAY_OUTER_LAYER, strayOuterLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_WITHER_SKELETON, skeletonLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_WITHER_SKELETON_INNER_ARMOR, innerArmorLayer);
        evt.registerLayerDefinition(ModClientRegistry.BABY_WITHER_SKELETON_OUTER_ARMOR, outerArmorLayer);
    }
}
