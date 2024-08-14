package fuzs.tinyskeletons.data;

import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.tinyskeletons.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;

public class ModEntityTypeTagProvider extends AbstractTagProvider.EntityTypes {

    public ModEntityTypeTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(ModRegistry.BABY_STRAY_ENTITY_TYPE.value());
        this.tag(EntityTypeTags.SKELETONS)
                .add(ModRegistry.BABY_SKELETON_ENTITY_TYPE.value(),
                        ModRegistry.BABY_WITHER_SKELETON_ENTITY_TYPE.value(),
                        ModRegistry.BABY_STRAY_ENTITY_TYPE.value()
                );
    }
}
