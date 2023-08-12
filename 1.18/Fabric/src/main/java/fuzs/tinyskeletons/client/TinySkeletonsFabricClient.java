package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.tinyskeletons.TinySkeletons;
import net.fabricmc.api.ClientModInitializer;

public class TinySkeletonsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletonsClient::new);
    }
}
