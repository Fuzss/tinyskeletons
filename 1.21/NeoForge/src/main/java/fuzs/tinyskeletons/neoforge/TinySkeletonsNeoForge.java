package fuzs.tinyskeletons.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.data.ModEntityTypeLootProvider;
import fuzs.tinyskeletons.data.ModEntityTypeTagProvider;
import fuzs.tinyskeletons.data.client.ModLanguageProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(TinySkeletons.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinySkeletonsNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletons::new);
        DataProviderHelper.registerDataProviders(TinySkeletons.MOD_ID,
                ModLanguageProvider::new,
                ModEntityTypeLootProvider::new,
                ModEntityTypeTagProvider::new
        );
    }
}
