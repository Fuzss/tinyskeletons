package fuzs.puzzlesworkshop.client.element;

import fuzs.puzzleslib.element.extension.ElementExtension;
import fuzs.puzzleslib.element.side.IClientElement;
import fuzs.puzzlesworkshop.client.renderer.entity.BabySkeletonRenderer;
import fuzs.puzzlesworkshop.element.TinySkeletonsElement;
import fuzs.puzzlesworkshop.entity.monster.BabySkeletonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class TinySkeletonsExtension extends ElementExtension<TinySkeletonsElement> implements IClientElement {

    public TinySkeletonsExtension(TinySkeletonsElement parent) {

        super(parent);
    }

    @Override
    public void setupClient2() {

        EntityType<BabySkeletonEntity> babySkeletonEntity = TinySkeletonsElement.BABY_SKELETON_ENTITY;
        EntityRendererManager entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.register(babySkeletonEntity, new BabySkeletonRenderer(entityRenderDispatcher));
        RenderingRegistry.registerEntityRenderingHandler(babySkeletonEntity, BabySkeletonRenderer::new);
    }

}
