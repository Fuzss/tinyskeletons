package fuzs.tinyskeletons.mixin;

import fuzs.tinyskeletons.api.event.player.EntityInteractCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerFabricMixin extends LivingEntity {

    protected PlayerFabricMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "interactOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"), cancellable = true)
    public void interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> callbackInfo) {
        Player player = (Player) (Object) this;
        Level world = player.getCommandSenderWorld();
        InteractionResult result = EntityInteractCallback.EVENT.invoker().interact(player, world, interactionHand, entity);
        if (result != null) callbackInfo.setReturnValue(result);
    }
}
