package fuzs.tinyskeletons.forge.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.client.TinySkeletonsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = TinySkeletons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TinySkeletonsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletonsClient::new);
    }
}
