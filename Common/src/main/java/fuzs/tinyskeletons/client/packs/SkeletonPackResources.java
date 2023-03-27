package fuzs.tinyskeletons.client.packs;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyStrayRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.BuiltInMetadata;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;

public class SkeletonPackResources implements PackResources {
    private static final PackMetadataSection VERSION_METADATA_SECTION = new PackMetadataSection(Component.literal("Teeny, tiny skeletons, send shivers down your spine..."), PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion()));
    private static final BuiltInMetadata BUILT_IN_METADATA = BuiltInMetadata.of(PackMetadataSection.TYPE, VERSION_METADATA_SECTION);
    private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation STRAY_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/stray.png");
    private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final BiMap<ResourceLocation, ResourceLocation> BABY_SKELETON_LOCATION = ImmutableBiMap.of(BabySkeletonRenderer.BABY_SKELETON_LOCATION, SKELETON_LOCATION, BabyStrayRenderer.BABY_STRAY_SKELETON_LOCATION, STRAY_SKELETON_LOCATION, BabyWitherSkeletonRenderer.BABY_WITHER_SKELETON_LOCATION, WITHER_SKELETON_LOCATION);

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        return null;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
        return null;
    }

    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {

    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return BABY_SKELETON_LOCATION.keySet().stream().map(ResourceLocation::getNamespace).collect(Collectors.toSet());
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
        return BUILT_IN_METADATA.get(deserializer);
    }

    @Override
    public String packId() {
        return TinySkeletons.MOD_ID;
    }

    @Override
    public void close() {

    }
}
