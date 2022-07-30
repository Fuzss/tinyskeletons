package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.tinyskeletons.TinySkeletons;
import net.fabricmc.api.ClientModInitializer;

public class TinySkeletonsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(TinySkeletons.MOD_ID).accept(new TinySkeletonsClient());
    }
}
