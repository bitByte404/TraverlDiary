import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.application.traveldiary"
    compileSdk = 34
    viewBinding {
        enable = true
    }
    defaultConfig {
        applicationId = "com.application.traveldiary"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    //Bmob
    implementation("io.github.bmob:android-sdk:3.9.4")
    implementation("io.reactivex.rxjava2:rxjava:2.2.8")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okio:okio:3.6.0")
    implementation("com.google.code.gson:gson:2.10.1")

    //lottie
    implementation("com.airbnb.android:lottie:6.1.0")

    //photoView
    implementation ("com.github.chrisbanes.photoview:library:+")
    implementation ("com.github.bumptech.glide:glide:3.7.0")

    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")

    //viewpager2
    implementation ("androidx.appcompat:appcompat:1.1.0-alpha05")
    implementation ("com.android.support:design:28.0.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0-beta02")

    //Gaode map
//    implementation(fileTree(mapOf("include" to listOf("*.jar"),"dir" to "libs")))
//    implementation("com.amap.api:3dmap:latest.integration")
//    implementation("com.amap.api:3dmap:4.0.0")
//    implementation("com.amap.api:location:3.0.0")
//    implementation("com.amap.api:search:4.0.0")

    //koHttp
    implementation ("com.squareup.okhttp3:okhttp:4.0.0")


}