package fuzs.tinyskeletons.mixin;

import fuzs.tinyskeletons.api.event.player.MobCreateCallback;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {

    @Redirect(method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getMaxSpawnClusterSize()I"))
    private static int getMaxSpawnClusterSize(Mob mob) {
        if (MobCreateCallback.EVENT.invoker().spawn(mob.level, mob, MobSpawnType.NATURAL)) {
            mob.discard();
        }
        return mob.getMaxSpawnClusterSize();
    }
}
