package fuzs.tinyskeletons.client;

import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.tinyskeletons.TinySkeletons;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = TinySkeletons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TinySkeletonsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientCoreServices.FACTORIES.clientModConstructor().accept(TinySkeletonsClient.INSTANCE);
    }
}
