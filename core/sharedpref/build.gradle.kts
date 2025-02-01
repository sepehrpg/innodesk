@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.hilt)
}

android {
    namespace = "com.example.sharedpref"
}

dependencies {
    api(project(":core:model"))

    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)


    //Sharedpref
    implementation(libs.androidx.preference)
    implementation(libs.okhttp3.core)
    implementation(libs.gson.core)
    implementation(libs.kotlin.reflect)

}