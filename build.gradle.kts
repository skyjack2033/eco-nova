plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    // Only exclude stubs that shadow real mod classes at runtime
    exclude("codechicken/**")
    exclude("net/minecraft/block/properties/**")
    exclude("net/minecraft/block/state/**")
    exclude("net/minecraft/util/math/**")
    exclude("net/minecraftforge/fluids/capability/**")
    exclude("net/minecraftforge/items/**")
    exclude("co/**")
    exclude("com/circulation/**")
    exclude("com/cleanroommc/**")
    exclude("mezz/**")
}
