plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appdoctruyen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appdoctruyen"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    //them thư viện picasso
    implementation("com.squareup.picasso:picasso:2.71828")
    //them drawelayout
    implementation("androidx.drawerlayout:drawerlayout:1.1.1")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
    implementation("com.github.bumptech.glide:glide:4.15.1")

    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

}
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        // jcenter() is deprecated, use mavenCentral or other repositories
//    }
//}
//
//// Corrected clean task definition
//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
