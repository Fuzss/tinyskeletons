package fuzs.tinyskeletons.client.packs;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.blaze3d.platform.NativeImage;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyStrayRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BabySkeletonPackResources extends AbstractModPackResources {
    private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation STRAY_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/stray.png");
    private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final BiMap<ResourceLocation, ResourceLocation> BABY_SKELETON_LOCATIONS = ImmutableBiMap.of(BabySkeletonRenderer.BABY_SKELETON_LOCATION, SKELETON_LOCATION, BabyStrayRenderer.BABY_STRAY_SKELETON_LOCATION, STRAY_SKELETON_LOCATION, BabyWitherSkeletonRenderer.BABY_WITHER_SKELETON_LOCATION, WITHER_SKELETON_LOCATION);
    private static final int VANILLA_SKELETON_TEXTURE_WIDTH = 64;
    private static final int VANILLA_SKELETON_TEXTURE_HEIGHT = 32;

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceManager resourceManager = minecraft.getResourceManager();
        ResourceLocation vanillaSkeletonLocation = BABY_SKELETON_LOCATIONS.get(location);
        if (vanillaSkeletonLocation == null) return null;
        Optional<Resource> resource = resourceManager.getResource(vanillaSkeletonLocation);
        if (resource.isPresent()) {
            try (NativeImage image = NativeImage.read(resource.get().open())) {
                if (image.getWidth() / image.getHeight() != VANILLA_SKELETON_TEXTURE_WIDTH / VANILLA_SKELETON_TEXTURE_HEIGHT) {
                    return minecraft.getVanillaPackResources().getResource(packType, vanillaSkeletonLocation);
                }
            } catch (IOException ignored) {

            }
            return resource.get()::open;
        }
        return null;
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return BABY_SKELETON_LOCATIONS.keySet().stream().map(ResourceLocation::getNamespace).collect(Collectors.toSet());
    }
}
