package fuzs.tinyskeletons;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class TinySkeletonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletons::new);
    }
}
