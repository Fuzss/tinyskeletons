package fuzs.tinyskeletons.mixin;

import fuzs.tinyskeletons.api.event.player.MobCreateCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(EntityType.class)
abstract class EntityTypeFabricMixin<T extends Entity> {

    @Inject(method = "spawn(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/nbt/CompoundTag;Ljava/util/function/Consumer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;ZZ)Lnet/minecraft/world/entity/Entity;", at = @At("TAIL"), cancellable = true)
    public void spawn(ServerLevel serverLevel, @Nullable CompoundTag compoundTag, @Nullable Consumer<T> consumer, BlockPos blockPos, MobSpawnType mobSpawnType, boolean shouldOffsetY, boolean shouldOffsetYMore, CallbackInfoReturnable<T> callback) {
        T entity = callback.getReturnValue();
        if (entity instanceof Mob mob && MobCreateCallback.EVENT.invoker().spawn(serverLevel, mob, mobSpawnType)) {
            mob.discard();
            callback.setReturnValue(null);
        }
    }
}
