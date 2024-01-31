package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ForgeClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay) {
        IClientItemExtensions.of(itemStack).getCustomRenderer().renderByItem(itemStack, ItemDisplayContext.NONE, poseStack, multiBufferSource, lightmap, overlay);
    }
}
