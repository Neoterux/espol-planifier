import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version("1.5.31")
    java
    application
    id("org.openjfx.javafxplugin") version("0.0.10")
    id("org.beryx.jlink") version("2.24.2")
}

group = "com.neoterux"
version = "0.5-dev"
val mclassName = "com.neoterux.espfier.Launcher"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.0")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.5.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
}

/**
 * Plugins configuration
 */
javafx {
    version = "16"
    modules("javafx.fxml", "javafx.controls")
}

java {
    modularity.inferModulePath.set(true)
}

application {
    mainModule.set("ntrx.planifier")
    mainClass.set(mclassName)
}
jlink {
    options.addAll("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    mainClass.set(mclassName)
    addExtraDependencies("javafx")
    launcher {
        name = "espol-planifier"
    }
    imageZip.set(project.file("${project.buildDir}/image-zip/img.zip"))
    jpackage {
        outputDir = "image-output"
        imageName = "Espol Planifier"
        installerName = "EspPlanifier-installer"
        skipInstaller = true
        with(org.gradle.internal.os.OperatingSystem.current()){
            if(isLinux)
                installerType = "appimage"
            else if (isMacOsX)
                installerType = "dmg"
            else
                installerType = "exe"
        }
//        installerType = "appimage"

        appVersion = "0.5-dev"
    }
}


/**
 * Tasks configurations
 */
val javaVersion = "16"
val compileKotlin: KotlinCompile by tasks

tasks.jar {
    manifest {
        attributes("Main-Class" to mclassName)
    }
}

tasks.compileKotlin {
//    dependsOn(":compileJava")
    kotlinOptions{
        targetCompatibility = javaVersion
        sourceCompatibility = javaVersion
    }
}

tasks.compileJava {
    targetCompatibility = javaVersion
    sourceCompatibility = javaVersion
    compileKotlin.destinationDirectory.set(destinationDirectory)
}

tasks.test {
    useJUnitPlatform()
}
