import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.0-RC2"
}

repositories {
    mavenCentral()
}

dependencies { }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn("clean")
    dependsOn(":plugin:assemble")
}

kotlin {
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
        val path = "${project.rootDir}/plugin/build/libs/plugin.jar"
        freeCompilerArgs.set(freeCompilerArgs.get() + "-Xplugin=$path")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
