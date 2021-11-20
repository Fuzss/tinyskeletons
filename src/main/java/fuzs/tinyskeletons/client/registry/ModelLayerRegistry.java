package fuzs.tinyskeletons.client.registry;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayerRegistry {
    private final String namespace;

    private ModelLayerRegistry(String namespace) {
        this.namespace = namespace;
    }

    public ModelLayerLocation register(String path) {
        return this.register(path, "main");
    }

    public ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(new ResourceLocation(this.namespace, path), layer);
    }

    public ModelLayerLocation registerInnerArmor(String path) {
        return this.register(path, "inner_armor");
    }

    public ModelLayerLocation registerOuterArmor(String path) {
        return this.register(path, "outer_armor");
    }

    public static ModelLayerRegistry of(String namespace) {
        return new ModelLayerRegistry(namespace);
    }
}
