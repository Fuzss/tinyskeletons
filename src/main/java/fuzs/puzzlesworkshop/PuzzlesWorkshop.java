package fuzs.puzzlesworkshop;

import fuzs.puzzleslib.PuzzlesLib;
import fuzs.puzzleslib.element.AbstractElement;
import fuzs.puzzleslib.element.ElementRegistry;
import fuzs.puzzlesworkshop.element.TinySkeletonsElement;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PuzzlesWorkshop.MODID)
public class PuzzlesWorkshop {

    public static final String MODID = "puzzlesworkshop";
    public static final String NAME = "Puzzles Workshop";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final AbstractElement TINY_SKELETONS = PuzzlesLib.create(MODID).register("tiny_skeletons", TinySkeletonsElement::new);

    public PuzzlesWorkshop() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        PuzzlesLib.setup(true);
    }

    private void onClientSetup(final FMLClientSetupEvent evt) {

        System.out.println("!!!!!");
//        ElementRegistry.load(evt, ModConfig.Type.CLIENT);
    }

}
