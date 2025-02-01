import com.android.build.gradle.internal.tasks.databinding.DataBindingGenBaseClassesTask
import com.google.protobuf.gradle.GenerateProtoTask
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool

plugins {
    alias(libs.plugins.project.android.library)
    alias(libs.plugins.project.android.hilt)
    //Proto
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.example.datastore_proto"
}


dependencies {
    implementation(project(":core:model"))

    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)




    /** Note: If you use the datastore-preferences-core artifact with Proguard, you must manually add Proguard rules
     * to your proguard-rules.pro file to keep your fields from being deleted. You can find the necessary rules here::
     * https://developer.android.com/topic/libraries/architecture/datastore
     * */
    //Proto
    // Alternatively - use the following artifact without an Android dependency.
    implementation(libs.androidx.dataStore.core)
    //
    implementation(libs.protobuf.kotlin.lite) //ex::import com.google.protobuf.InvalidProtocolBufferException
    //implementation("com.google.protobuf:protobuf-javalite:3.25.2") //ex::import com.google.protobuf.InvalidProtocolBufferException
}



// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}
androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val protoTask =
                project.tasks.getByName("generate" + variant.name.replaceFirstChar { it.uppercaseChar() } + "Proto") as GenerateProtoTask
            project.tasks.getByName("ksp" + variant.name.replaceFirstChar { it.uppercaseChar() } + "Kotlin") {
                dependsOn(protoTask)
                (this as org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>).setSource(
                    protoTask.outputBaseDir
                )
            }
        }
    }
}
/*
androidComponents.beforeVariants {
    android.sourceSets.register(it.name) {
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}*/
