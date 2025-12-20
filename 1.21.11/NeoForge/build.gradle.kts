plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-neoforge")
}

dependencies {
    modCompileOnly(libs.puzzleslib.common)
    modApi(libs.puzzleslib.neoforge)
}

multiloader {
    modFile {
        toml {
            extraProperties("lithium:options", mapOf("mixin.world.chunk_access" to false))
        }
    }
}
