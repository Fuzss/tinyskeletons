package fuzs.tinyskeletons.client.packs;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.blaze3d.platform.NativeImage;
import fuzs.puzzleslib.api.resources.v1.AbstractModPackResources;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyStrayRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
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

    private final ResourceManager resourceManager;
    private final VanillaPackResources vanillaPackResources;

    {
        Minecraft minecraft = Minecraft.getInstance();
        this.resourceManager = minecraft.getResourceManager();
        this.vanillaPackResources = minecraft.getVanillaPackResources();
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        ResourceLocation vanillaSkeletonLocation = BABY_SKELETON_LOCATIONS.get(location);
        if (vanillaSkeletonLocation == null) return null;
        // use vanilla skeleton texture for the baby
        Optional<Resource> resource = this.resourceManager.getResource(vanillaSkeletonLocation);
        if (resource.isPresent()) {
            try (NativeImage image = NativeImage.read(resource.get().open())) {
                // check the vanilla skeleton texture aspect ratio; some mods using OptiFine change the texture file completely since they also change the skeleton model, which breaks tiny skeletons
                // make sure to check the aspect ratio instead of absolute width / height to support higher resolution resource packs
                // in that case fall back to the skeleton texture from the vanilla assets pack
                if (image.getWidth() / image.getHeight() != VANILLA_SKELETON_TEXTURE_WIDTH / VANILLA_SKELETON_TEXTURE_HEIGHT) {
                    return this.vanillaPackResources.getResource(packType, vanillaSkeletonLocation);
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
