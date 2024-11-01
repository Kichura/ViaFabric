dependencies {
    minecraft("com.mojang:minecraft:1.21.2")
    mappings("net.fabricmc:yarn:1.21.2+build.1:v2")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.106.1+1.21.2")
    modImplementation("com.terraformersmc:modmenu:12.0.0-beta.1")
}

tasks.compileJava {
    options.release.set(21)
}