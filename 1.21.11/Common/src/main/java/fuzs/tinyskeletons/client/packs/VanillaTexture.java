package fuzs.tinyskeletons.client.packs;

import fuzs.tinyskeletons.TinySkeletons;
import net.minecraft.resources.Identifier;

public record VanillaTexture(Identifier id, Identifier vanillaId, int textureWidth, int textureHeight) {

    public VanillaTexture(String texturePath) {
        this(TinySkeletons.id(texturePath), Identifier.withDefaultNamespace(texturePath));
    }

    public VanillaTexture(Identifier id, Identifier vanillaId) {
        this(id, vanillaId, 64, 32);
    }
}
