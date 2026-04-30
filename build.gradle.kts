plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    // Exclude AE2 compatibility stubs that shadow real jar classes at runtime
    exclude("appeng/me/storage/AbstractCellInventory.class")
    exclude("appeng/me/storage/BasicCellInventoryHandler.class")
}
