package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.mixin.client.accessor.BlockRenderDispatcherAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;
import java.util.Optional;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay) {
        ((BlockRenderDispatcherAccessor) Minecraft.getInstance().getBlockRenderer()).getBlockEntityRenderer().renderByItem(itemStack, ItemTransforms.TransformType.NONE, poseStack, multiBufferSource, lightmap, overlay);
    }

    @Override
    public Optional<Path> findModResource(String id, String... pathName) {
        return FabricLoader.getInstance().getModContainer(id).flatMap(modContainer -> modContainer.findPath(String.join("/", pathName)));
    }
}
