package fuzs.tinyskeletons;

import fuzs.puzzleslib.core.CoreServices;
import fuzs.tinyskeletons.handler.BabyConversionHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(TinySkeletons.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinySkeletonsForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(TinySkeletons.MOD_ID).accept(new TinySkeletons());
        registerHandlers();
    }

    private static void registerHandlers() {
        final BabyConversionHandler babyConversionHandler = new BabyConversionHandler();
        MinecraftForge.EVENT_BUS.addListener((final LivingPackSizeEvent evt) -> {
            // we hijack this event for replacing naturally spawned adult mobs
            // there are two other events fired before this, but only this one works as the entity is already added to the world here
            // removing the entity when not added to a world yet will log a warning every time, might also have worse side effects
            boolean result = babyConversionHandler.onMobCreate(evt.getEntity().level, evt.getEntity(), null);
            if (result) evt.getEntity().discard();
        });
        MinecraftForge.EVENT_BUS.addListener((final LivingSpawnEvent.SpecialSpawn evt) -> {
            // only respond to event firing from EntityType::spawn
            // the event is fired at two more places, but those are bugged and don't prevent the entity from spawning when canceled
            // they only prevent any equipment from being added for some reason
            // also exclude summoned by command as this would break forcefully spawning an adult skeleton since there is no baby flag as with zombies which could force that otherwise
            if (evt.getSpawnReason() != MobSpawnType.NATURAL && evt.getSpawnReason() != MobSpawnType.SPAWNER && evt.getSpawnReason() != MobSpawnType.COMMAND) {
                boolean result = babyConversionHandler.onMobCreate(evt.getLevel(), evt.getEntity(), evt.getSpawnReason());
                if (result) evt.setCanceled(true);
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final PlayerInteractEvent.EntityInteract evt) -> {
            InteractionResult result = babyConversionHandler.onEntityInteract(evt.getEntity(), evt.getLevel(), evt.getHand(), evt.getTarget());
            if (result != null) {
                evt.setCanceled(true);
                evt.setCancellationResult(result);
            }
        });
    }
}
