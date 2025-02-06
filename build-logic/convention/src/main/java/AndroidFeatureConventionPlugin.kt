/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** ??????? */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                //JUST FOR TEST
                //Application Feature Module
                apply("project.android.application")
                apply("project.android.application.compose")

                //REAL
                //Library Feature Module
                //apply("project.android.library")
                //apply("project.android.library.compose")

                apply("project.compose.component")
                apply("project.android.hilt")
            }

            /*extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
                }
            }*/

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:common"))
                //add("implementation", project(":core:ui"))

                //add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                //add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                //add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            }
        }
    }
}
