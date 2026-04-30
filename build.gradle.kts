plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    // Exclude stubs that shadow real mod classes at runtime
    // appeng/** stubs are at wrong packages for AE2 rv3 — real classes come from AE2 dependency
    exclude("appeng/**")
    exclude("codechicken/**")
    exclude("co/**")
    exclude("com/circulation/**")
    exclude("com/cleanroommc/**")
    exclude("mezz/**")
}
