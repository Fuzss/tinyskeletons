package fuzs.tinyskeletons.fabric.server.level;

import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

/**
 * <ul>
 * <li><a href="https://github.com/MinecraftForge/MinecraftForge/pull/7697">Bypass chunk future chain when processing loads
 * and getChunk called.</a></li>
 * <li><a href="https://github.com/neoforged/NeoForge/pull/99">Fix chunk future chain not being bypassed for entity loads
 * in 1.17+</a></li>
 * <li><a href="https://github.com/neoforged/NeoForge/pull/110">Fix another misapplied component of the chunk future
 * patch</a></li>
 * </ul>
 */
public interface CurrentlyLoadingChunkHolder {

    void tinyskeletons$setCurrentlyLoadingChunk(@Nullable LevelChunk levelChunk);

    @Nullable LevelChunk tinyskeletons$getCurrentlyLoadingChunk();
}
