package fuzs.puzzlesworkshop.element;

import fuzs.puzzleslib.element.extension.ClientExtensibleElement;
import fuzs.puzzlesworkshop.client.element.TimeToLiveExtension;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class TimeToLiveElement extends ClientExtensibleElement<TimeToLiveExtension> {

    public TimeToLiveElement() {

        super(element -> new TimeToLiveExtension((TimeToLiveElement) element));
    }

    @Override
    public String[] getDescription() {

        return new String[]{"Helpful utils when working with TNT."};
    }

    @Override
    public void constructCommon() {

        this.addListener(this::onEntityInteract);
    }

    private void onEntityInteract(final PlayerInteractEvent.EntityInteract evt) {

        if (evt.getTarget() instanceof TNTEntity && evt.getItemStack().getItem() instanceof ShearsItem) {

            World level = evt.getWorld();
            level.playSound(null, evt.getTarget().blockPosition(), SoundEvents.SNOW_GOLEM_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!level.isClientSide()) {

                evt.getTarget().spawnAtLocation(new ItemStack(Items.TNT));
                evt.getTarget().remove();
                evt.getItemStack().hurtAndBreak(1, evt.getPlayer(), player -> player.broadcastBreakEvent(evt.getHand()));
                evt.setCancellationResult(ActionResultType.SUCCESS);
            }

            evt.setCancellationResult(ActionResultType.CONSUME);
        }
    }

}
