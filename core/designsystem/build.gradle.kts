@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.library.compose)
    alias(libs.plugins.project.compose.component)
}

android {
    namespace = "com.example.designsystem"
}


dependencies {
    implementation(libs.androidx.test.ext)

    //
    //Calender
    // The view calendar library for Android
    //implementation("com.kizitonwose.calendar:view:<latest-version>")
    // The compose calendar library for Android
    //implementation("com.kizitonwose.calendar:compose:2.6.2")
    // Add the multiplatform calendar library to your project's build.gradle.kts
    // The calendar library for compose multiplatform projects
    // Supports Android, iOS, WasmJs and Desktop platforms
    implementation(libs.compose.calender.multiplatform)

    //Color Picker
    api(libs.colorpicker)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // To use the androidx.test.espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    //androidTestImplementation(libs.androidx.test.espresso.core)
}