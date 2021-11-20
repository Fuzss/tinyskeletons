package fuzs.tinyskeletons;

import fuzs.puzzleslib.PuzzlesLib;
import fuzs.puzzleslib.element.AbstractElement;
import fuzs.tinyskeletons.element.TinySkeletonsElement;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TinySkeletons.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinySkeletons {
    public static final String MODID = "tinyskeletons";
    public static final String NAME = "Tiny Skeletons";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final AbstractElement TINY_SKELETONS = PuzzlesLib.create(MODID).register("tiny_skeletons", TinySkeletonsElement::new);

    public TinySkeletons() {
        PuzzlesLib.setup(false);
    }
}
