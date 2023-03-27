package fuzs.tinyskeletons;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.tinyskeletons.api.event.player.MobCreateCallback;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import net.fabricmc.api.ModInitializer;

public class TinySkeletonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletons::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // TODO migrate to common event in Puzzles Lib for 1.19.4 with Forge's new spawning event
        MobCreateCallback.EVENT.register(BabyConversionHandler::onMobCreate);
    }
}
