package fuzs.tinyskeletons.client.core;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.ModList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ForgeClientAbstractions implements ClientAbstractions {

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int lightmap, int overlay) {
        IClientItemExtensions.of(itemStack).getCustomRenderer().renderByItem(itemStack, ItemTransforms.TransformType.NONE, poseStack, multiBufferSource, lightmap, overlay);
    }

    @Override
    public Optional<Path> findModResource(String id, String... pathName) {
        return Optional.of(ModList.get().getModFileById(id).getFile().findResource(pathName)).filter(Files::exists);
    }
}
