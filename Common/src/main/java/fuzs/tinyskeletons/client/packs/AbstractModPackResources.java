package fuzs.tinyskeletons.client.packs;

import fuzs.tinyskeletons.client.core.ClientAbstractions;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.BuiltInMetadata;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.function.Supplier;

public abstract class AbstractModPackResources implements PackResources {
    private String id;
    private BuiltInMetadata metadata;

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... elements) {
        String path = String.join("/", elements);
        if ("pack.png".equals(path)) {
            return ClientAbstractions.INSTANCE.findModResource(this.id, "mod_logo.png").<IoSupplier<InputStream>>map(modResource -> {
                return () -> Files.newInputStream(modResource);
            }).orElse(null);
        }
        return null;
    }

    @Override
    public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {

    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) {
        return this.metadata.get(deserializer);
    }

    @Override
    public String packId() {
        return this.id;
    }

    @Override
    public void close() {

    }

    public static RepositorySource clientPack(Supplier<AbstractModPackResources> factory, String id, Component title, Component description, boolean required, boolean fixedPosition) {
        return consumer -> {
            consumer.accept(buildPack(PackType.CLIENT_RESOURCES, factory, id, title, description, required, fixedPosition));
        };
    }

    public static RepositorySource serverPack(Supplier<AbstractModPackResources> factory, String id, Component title, Component description, boolean required, boolean fixedPosition) {
        return consumer -> {
            consumer.accept(buildPack(PackType.SERVER_DATA, factory, id, title, description, required, fixedPosition));
        };
    }

    private static Pack buildPack(PackType packType, Supplier<AbstractModPackResources> factory, String id, Component title, Component description, boolean required, boolean fixedPosition) {
        PackMetadataSection metadataSection = new PackMetadataSection(description, packType.getVersion(SharedConstants.getCurrentVersion()));
        BuiltInMetadata metadata = BuiltInMetadata.of(PackMetadataSection.TYPE, metadataSection);
        Pack.Info info = new Pack.Info(description, metadataSection.getPackFormat(), FeatureFlagSet.of());
        return Pack.create(id, title, required, $ -> {
            AbstractModPackResources packResources = factory.get();
            packResources.id = id;
            packResources.metadata = metadata;
            return packResources;
        }, info, packType, Pack.Position.TOP, fixedPosition, PackSource.BUILT_IN);
    }
}
