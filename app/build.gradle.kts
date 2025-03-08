plugins {
    alias(libs.plugins.project.android.application) // android application(configuration)
    alias(libs.plugins.project.android.application.compose) //compose application
    alias(libs.plugins.project.android.lint) // lint
    alias(libs.plugins.project.compose.component) // all jetpack compose component
    alias(libs.plugins.project.android.hilt) // hilt
}

android {
    namespace = "com.innodesk.app"
    defaultConfig {
        applicationId = "com.innodesk.app"
        versionCode = 1
        versionName = "1.0"
        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }


}

dependencies {



    /** Add Module */
    /** Add Module */
    implementation(project(":core:designsystem"))
    implementation(project(":feature:project-management"))
    implementation(project(":core:data"))


    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.9")


    implementation(libs.material) //ex:androidx.activity.ComponentActivity
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)


    implementation(libs.androidx.adaptive)
    implementation (libs.androidx.adaptive.layout)
    implementation (libs.androidx.adaptive.navigation)
    implementation(libs.kotlinx.datetime)


    //implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.appcompat)
    //testImplementation(libs.junit)
    //androidTestImplementation(libs.androidx.junit)
    //androidTestImplementation(libs.androidx.espresso.core)


    /*implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)*/
}

