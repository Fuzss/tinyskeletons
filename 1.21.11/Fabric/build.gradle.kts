plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-fabric")
}

dependencies {
    modApi(libs.fabricapi.fabric)
    modApi(libs.puzzleslib.fabric)
}

multiloader {
    modFile {
        json {
            customData.put("lithium:options", mapOf("mixin.world.chunk_access" to false))
        }
    }

    mixins {
        mixin("ChunkStatusTasksFabricMixin", "GenerationChunkHolderFabricMixin", "ServerChunkCacheFabricMixin")
    }
}
