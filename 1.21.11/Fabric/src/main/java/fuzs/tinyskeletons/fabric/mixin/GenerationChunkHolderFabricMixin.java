package fuzs.tinyskeletons.fabric.mixin;

import fuzs.tinyskeletons.fabric.server.level.CurrentlyLoadingChunkHolder;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GenerationChunkHolder.class)
abstract class GenerationChunkHolderFabricMixin implements CurrentlyLoadingChunkHolder {
    @Unique
    @Nullable
    private LevelChunk tinyskeletons$currentlyLoadingChunk;

    @Override
    public @Nullable LevelChunk tinyskeletons$getCurrentlyLoadingChunk() {
        return this.tinyskeletons$currentlyLoadingChunk;
    }

    @Override
    public void tinyskeletons$setCurrentlyLoadingChunk(@Nullable LevelChunk levelChunk) {
        this.tinyskeletons$currentlyLoadingChunk = levelChunk;
    }
}
