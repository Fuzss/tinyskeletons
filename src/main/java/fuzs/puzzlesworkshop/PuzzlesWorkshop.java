package fuzs.puzzlesworkshop;

import fuzs.puzzleslib.PuzzlesLib;
import fuzs.puzzleslib.element.AbstractElement;
import fuzs.puzzlesworkshop.element.TimeToLiveElement;
import fuzs.puzzlesworkshop.element.TinySkeletonsElement;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PuzzlesWorkshop.MODID)
public class PuzzlesWorkshop {
    public static final String MODID = "puzzlesworkshop";
    public static final String NAME = "Puzzles Workshop";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final AbstractElement TINY_SKELETONS = PuzzlesLib.create(MODID).register("tiny_skeletons", TinySkeletonsElement::new);
    public static final AbstractElement TIME_TO_LIVE = PuzzlesLib.create(MODID).register("time_to_live", TimeToLiveElement::new);

    public PuzzlesWorkshop() {
        PuzzlesLib.setup(true);
    }
}
