package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;
import java.util.Optional;

public interface ClientAbstractions {
    ClientAbstractions INSTANCE = ServiceProviderHelper.load(ClientAbstractions.class);

    void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay);

    Optional<Path> findModResource(String id, String... pathName);
}
