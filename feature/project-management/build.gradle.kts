plugins {
    alias(libs.plugins.project.android.feature)
}

android {
    namespace = "com.innodesk.project_management"
}

dependencies {
    api(project(":core:database"))

    //implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.appcompat)
    //implementation(libs.material)
    //testImplementation(libs.junit)
    //androidTestImplementation(libs.androidx.test.ext.junit)
    //androidTestImplementation(libs.androidx.test.espresso.core)

    //implementation(libs.androidx.lifecycle.runtime.ktx)
    //implementation(libs.androidx.activity.compose)
    //implementation(platform(libs.androidx.compose.bom))
    //implementation(libs.androidx.compose.ui.ui)
    //implementation(libs.androidx.compose.ui.ui.graphics)
    //implementation(libs.androidx.compose.ui.tooling.preview)
    //implementation(libs.androidx.compose.material3)
    //androidTestImplementation(platform(libs.androidx.compose.bom))
    //androidTestImplementation(libs.androidx.compose.ui.test)
    //debugImplementation(libs.androidx.compose.ui.tooling)
    //debugImplementation(libs.androidx.compose.ui.testManifest)
}