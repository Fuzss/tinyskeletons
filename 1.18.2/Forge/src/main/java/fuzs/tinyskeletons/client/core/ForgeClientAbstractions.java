package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.RenderProperties;

public class ForgeClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay) {
        RenderProperties.get(itemStack).getItemStackRenderer().renderByItem(itemStack, ItemTransforms.TransformType.NONE, poseStack, multiBufferSource, lightmap, overlay);
    }
}
