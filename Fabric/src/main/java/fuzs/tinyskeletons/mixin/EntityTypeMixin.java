package fuzs.tinyskeletons.mixin;

import fuzs.tinyskeletons.api.event.player.MobCreateCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin<T extends Entity> {

    @Inject(method = "spawn(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;ZZ)Lnet/minecraft/world/entity/Entity;", at = @At("TAIL"), cancellable = true)
    public void spawn(ServerLevel serverLevel, @Nullable CompoundTag compoundTag, @Nullable Component component, @Nullable Player player, BlockPos blockPos, MobSpawnType mobSpawnType, boolean bl, boolean bl2, CallbackInfoReturnable<T> callbackInfo) {
        T entity = callbackInfo.getReturnValue();
        if (entity instanceof Mob mob && MobCreateCallback.EVENT.invoker().spawn(serverLevel, mob, mobSpawnType)) {
            mob.discard();
            callbackInfo.setReturnValue(null);
        }
    }
}
