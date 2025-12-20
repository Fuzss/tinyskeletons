package fuzs.tinyskeletons.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.tinyskeletons.fabric.server.level.CurrentlyLoadingChunkHolder;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkCache.class)
abstract class ServerChunkCacheFabricMixin extends ChunkSource {

    @Inject(
            method = "getChunk",
            at = @At(value = "CONSTANT", args = "stringValue=getChunkCacheMiss", shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void getChunk(int x, int z, ChunkStatus chunkStatus, boolean requireChunk, CallbackInfoReturnable<@Nullable ChunkAccess> callback, @Local long chunkPos) {
        ChunkHolder chunkHolder = this.getVisibleChunkIfPresent(chunkPos);
        if (chunkHolder != null
                && ((CurrentlyLoadingChunkHolder) chunkHolder).tinyskeletons$getCurrentlyLoadingChunk() != null) {
            callback.setReturnValue(((CurrentlyLoadingChunkHolder) chunkHolder).tinyskeletons$getCurrentlyLoadingChunk());
        }
    }

    @Shadow
    @Nullable
    private ChunkHolder getVisibleChunkIfPresent(long chunkPos) {
        throw new RuntimeException();
    }

    @Inject(
            method = "getChunkNow", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ChunkHolder;getChunkIfPresent(Lnet/minecraft/world/level/chunk/status/ChunkStatus;)Lnet/minecraft/world/level/chunk/ChunkAccess;",
            shift = At.Shift.BEFORE
    ), cancellable = true
    )
    public void getChunkNow(int chunkX, int chunkZ, CallbackInfoReturnable<@Nullable LevelChunk> callback, @Local ChunkHolder chunkHolder) {
        if (chunkHolder != null
                && ((CurrentlyLoadingChunkHolder) chunkHolder).tinyskeletons$getCurrentlyLoadingChunk() != null) {
            callback.setReturnValue(((CurrentlyLoadingChunkHolder) chunkHolder).tinyskeletons$getCurrentlyLoadingChunk());
        }
    }
}
