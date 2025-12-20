package fuzs.tinyskeletons.data.tags;

import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.BABY_BOGGED_THROWABLES_ITEM_TAG).add(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
        this.tag(ModRegistry.BABY_PARCHED_THROWABLES_ITEM_TAG).add(Items.SAND);
        this.tag(ModRegistry.BABY_STRAY_THROWABLES_ITEM_TAG).add(Items.SNOWBALL);
    }
}
