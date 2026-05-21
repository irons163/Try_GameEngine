plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.try_gameengine"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.try_gameengine"
        minSdk = 11
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.1704250"
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDirs("res")
            assets.srcDirs("assets")
            jniLibs.srcDirs("libs")
        }
    }

    compileOptions {
        encoding = "UTF-8"
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(fileTree("libs") { include("*.jar") })
}

tasks.withType<JavaCompile>().configureEach {
    exclude(
        "com/example/try_gameengine/test/**",
        "com/example/try_gameengine/extension_module/skill/SkillTest.java",
    )
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    exclude(
        "com/example/try_gameengine/test/**",
        "com/example/try_gameengine/extension_module/skill/SkillTest.kt",
    )
}
