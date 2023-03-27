package fuzs.tinyskeletons;

import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.tinyskeletons.api.event.player.MobCreateCallback;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

public class TinySkeletonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(TinySkeletons.MOD_ID, TinySkeletons::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        MobCreateCallback.EVENT.register(BabyConversionHandler::onMobCreate);
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            InteractionResult interactionResult = BabyConversionHandler.onEntityInteract(player, world, hand, entity);
            return interactionResult == null ? InteractionResult.PASS : interactionResult;
        });
    }
}
