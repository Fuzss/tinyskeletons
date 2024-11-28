package fuzs.tinyskeletons.neoforge.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.tinyskeletons.client.core.ClientAbstractions;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class NeoForgeClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        IClientItemExtensions.of(itemStack).getCustomRenderer().renderByItem(itemStack, ItemDisplayContext.NONE, poseStack, multiBufferSource,
                packedLight,
                packedOverlay
        );
    }
}
