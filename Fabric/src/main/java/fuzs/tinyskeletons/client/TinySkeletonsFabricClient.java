package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;

public class TinySkeletonsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor().accept(TinySkeletonsClient.INSTANCE);
    }
}
