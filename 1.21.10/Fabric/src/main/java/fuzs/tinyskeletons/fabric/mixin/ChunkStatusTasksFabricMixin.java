package fuzs.tinyskeletons.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.tinyskeletons.fabric.server.level.CurrentlyLoadingChunkHolder;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatusTasks;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkStatusTasks.class)
abstract class ChunkStatusTasksFabricMixin {

    @Inject(
            method = "lambda$full$2(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/chunk/status/WorldGenContext;Lnet/minecraft/server/level/GenerationChunkHolder;)Lnet/minecraft/world/level/chunk/ChunkAccess;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/LevelChunk;runPostLoad()V",
                    shift = At.Shift.BEFORE
            )
    )
    private static void full(ChunkAccess chunkAccess, WorldGenContext context, GenerationChunkHolder chunkHolder, CallbackInfoReturnable<ChunkAccess> callback, @Local LevelChunk levelChunk) {
        ((CurrentlyLoadingChunkHolder) chunkHolder).tinyskeletons$setCurrentlyLoadingChunk(levelChunk);
    }

    @Inject(
            method = "lambda$full$2(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/chunk/status/WorldGenContext;Lnet/minecraft/server/level/GenerationChunkHolder;)Lnet/minecraft/world/level/chunk/ChunkAccess;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/LevelChunk;setUnsavedListener(Lnet/minecraft/world/level/chunk/LevelChunk$UnsavedListener;)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void full(ChunkAccess chunkAccess, WorldGenContext context, GenerationChunkHolder chunkHolder, CallbackInfoReturnable<ChunkAccess> callback) {
        ((CurrentlyLoadingChunkHolder) chunkHolder).tinyskeletons$setCurrentlyLoadingChunk(null);
    }
}
