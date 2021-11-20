package fuzs.tinyskeletons.client.element;

import fuzs.puzzleslib.element.extension.ElementExtension;
import fuzs.puzzleslib.element.side.IClientElement;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import fuzs.tinyskeletons.element.TinySkeletonsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.StrayRenderer;

public class TinySkeletonsExtension extends ElementExtension<TinySkeletonsElement> implements IClientElement {
    public TinySkeletonsExtension(TinySkeletonsElement parent) {
        super(parent);
    }

    @Override
    public void setupClient2() {
        EntityRendererManager entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.register(TinySkeletonsElement.BABY_SKELETON_ENTITY, new BabySkeletonRenderer(entityRenderDispatcher));
        entityRenderDispatcher.register(TinySkeletonsElement.BABY_WITHER_SKELETON_ENTITY, new BabyWitherSkeletonRenderer(entityRenderDispatcher));
        entityRenderDispatcher.register(TinySkeletonsElement.BABY_STRAY_ENTITY, new StrayRenderer(entityRenderDispatcher));
    }
}
