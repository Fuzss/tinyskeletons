package fuzs.tinyskeletons;

import fuzs.puzzleslib.core.CoreServices;
import fuzs.tinyskeletons.api.event.player.EntityInteractCallback;
import fuzs.tinyskeletons.api.event.player.MobCreateCallback;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import net.fabricmc.api.ModInitializer;

public class TinySkeletonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor().accept(TinySkeletons.INSTANCE);
        registerHandlers();
    }

    private static void registerHandlers() {
        final BabyConversionHandler handler = new BabyConversionHandler();
        MobCreateCallback.EVENT.register(handler::onMobCreate);
        EntityInteractCallback.EVENT.register(handler::onEntityInteract);
    }
}
