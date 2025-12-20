package fuzs.tinyskeletons.neoforge;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.tinyskeletons.TinySkeletons;
import fuzs.tinyskeletons.data.loot.ModEntityTypeLootProvider;
import fuzs.tinyskeletons.data.tags.ModEntityTypeTagProvider;
import fuzs.tinyskeletons.data.tags.ModItemTagProvider;
import net.neoforged.fml.common.Mod;

@Mod(TinySkeletons.MOD_ID)
public class TinySkeletonsNeoForge {

    public TinySkeletonsNeoForge() {
        ModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletons::new);
        DataProviderHelper.registerDataProviders(TinySkeletons.MOD_ID,
                ModEntityTypeLootProvider::new,
                ModEntityTypeTagProvider::new,
                ModItemTagProvider::new);
    }
}
