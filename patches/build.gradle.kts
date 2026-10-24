group = "dk.lasserewers"

patches {
    about {
        name = "Lasses patches"
        description = "Lasse Rewers' own patches for use with Morphe."
        source = "git@github.com:lasserewers/lasse-patches.git"
        author = "Lasse Rewers"
        contact = "https://github.com/lasserewers/lasse-patches/issues"
        website = "https://github.com/lasserewers/lasse-patches"
        license = "GPLv3"
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}
