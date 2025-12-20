package fuzs.tinyskeletons.client.packs;

import com.mojang.blaze3d.platform.NativeImage;
import fuzs.puzzleslib.api.resources.v1.AbstractModPackResources;
import fuzs.tinyskeletons.client.renderer.entity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkeletonTextureCopiesPackResources extends AbstractModPackResources {
    private static final Map<Identifier, VanillaTexture> VANILLA_TEXTURES = Stream.of(BabySkeletonRenderer.SKELETON_TEXTURE,
                    BabyWitherSkeletonRenderer.WITHER_SKELETON_TEXTURE,
                    BabyStrayRenderer.STRAY_SKELETON_TEXTURE,
                    BabyStrayRenderer.STRAY_CLOTHES_TEXTURE,
                    BabyBoggedRenderer.BOGGED_SKELETON_TEXTURE,
                    BabyBoggedRenderer.BOGGED_OUTER_LAYER_TEXTURE,
                    BabyParchedRenderer.PARCHED_SKELETON_TEXTURE)
            .collect(Collectors.toMap(VanillaTexture::id, Function.identity()));

    private final ResourceManager resourceManager;
    private final VanillaPackResources vanillaPackResources;

    {
        Minecraft minecraft = Minecraft.getInstance();
        this.resourceManager = minecraft.getResourceManager();
        this.vanillaPackResources = minecraft.getVanillaPackResources();
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType packType, Identifier identifier) {
        VanillaTexture vanillaTexture = VANILLA_TEXTURES.get(identifier);
        if (vanillaTexture == null) {
            return null;
        } else {
            Optional<Resource> resource = this.resourceManager.getResource(vanillaTexture.vanillaId());
            if (resource.isPresent()) {
                try (NativeImage nativeImage = NativeImage.read(resource.get().open())) {
                    // check the vanilla skeleton texture aspect ratio; some mods using OptiFine change the texture file completely since they also change the skeleton model, which breaks tiny skeletons
                    // make sure to check the aspect ratio instead of absolute width / height to support higher resolution resource packs
                    // in that case fall back to the skeleton texture from the vanilla assets pack
                    if (nativeImage.getWidth() / nativeImage.getHeight()
                            != vanillaTexture.textureWidth() / vanillaTexture.textureHeight()) {
                        return this.vanillaPackResources.getResource(packType, vanillaTexture.vanillaId());
                    }
                } catch (IOException exception) {
                    // NO-OP
                }
                return resource.get()::open;
            } else {
                return null;
            }
        }
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return VANILLA_TEXTURES.keySet().stream().map(Identifier::getNamespace).collect(Collectors.toSet());
    }
}
