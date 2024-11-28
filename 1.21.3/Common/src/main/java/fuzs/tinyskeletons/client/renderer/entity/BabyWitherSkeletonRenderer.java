package fuzs.tinyskeletons.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.init.ModClientRegistry;
import fuzs.tinyskeletons.client.model.SkullCarryingSkeletonModel;
import fuzs.tinyskeletons.client.renderer.entity.layers.HeldSkullItemLayer;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeleton;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class BabyWitherSkeletonRenderer extends MobRenderer<BabyWitherSkeleton, SkullCarryingSkeletonModel<BabyWitherSkeleton>> {
    public static final ResourceLocation BABY_WITHER_SKELETON_LOCATION = TinySkeletons.id(
            "textures/entity/skeleton/baby_wither_skeleton.png");

    public BabyWitherSkeletonRenderer(EntityRendererProvider.Context context) {
        this(context, ModClientRegistry.BABY_WITHER_SKELETON, ModClientRegistry.BABY_WITHER_SKELETON_INNER_ARMOR,
                ModClientRegistry.BABY_WITHER_SKELETON_OUTER_ARMOR
        );
    }

    public BabyWitherSkeletonRenderer(EntityRendererProvider.Context context, ModelLayerLocation entityLocation, ModelLayerLocation innerArmorLocation, ModelLayerLocation outerArmorLocation) {
        super(context, new SkullCarryingSkeletonModel<>(context.bakeLayer(entityLocation)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new HeldSkullItemLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(
                new HumanoidArmorLayer<>(this, new SkullCarryingSkeletonModel<>(context.bakeLayer(innerArmorLocation)),
                        new SkullCarryingSkeletonModel<>(context.bakeLayer(outerArmorLocation)),
                        context.getModelManager()
                ));
    }

    @Override
    public ResourceLocation getTextureLocation(BabyWitherSkeleton babyWitherSkeleton) {
        return BABY_WITHER_SKELETON_LOCATION;
    }

    @Override
    protected void scale(BabyWitherSkeleton babyWitherSkeleton, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}