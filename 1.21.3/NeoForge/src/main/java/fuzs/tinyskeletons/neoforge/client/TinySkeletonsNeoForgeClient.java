package fuzs.tinyskeletons.neoforge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.TinySkeletonsClient;
import fuzs.tinyskeletons.data.client.ModLanguageProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = TinySkeletons.MOD_ID, dist = Dist.CLIENT)
public class TinySkeletonsNeoForgeClient {

    public TinySkeletonsNeoForgeClient() {
        ClientModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletonsClient::new);
        DataProviderHelper.registerDataProviders(TinySkeletons.MOD_ID, ModLanguageProvider::new);
    }
}
