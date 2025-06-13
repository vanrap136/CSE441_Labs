plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appjsonsxmlparser"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appjsonsxmlparser"
        minSdk = 25
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
// ... các phần khác của build.gradle.kts
        // Các dependencies mặc định của dự án
        //noinspection UseTomlInstead,GradleDependency
        implementation("androidx.core:core-ktx:1.12.0") // Có thể phiên bản khác tùy dự án
        //noinspection UseTomlInstead,GradleDependency
        implementation("androidx.appcompat:appcompat:1.6.1")
        //noinspection UseTomlInstead,GradleDependency
        implementation("com.google.android.material:material:1.11.0")
        //noinspection UseTomlInstead,GradleDependency
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        //noinspection UseTomlInstead
        testImplementation("junit:junit:4.13.2")
        //noinspection UseTomlInstead,GradleDependency
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        //noinspection UseTomlInstead,GradleDependency
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        // THƯ VIỆN CẦN THIẾT CHO BÀI 4:
        //noinspection UseTomlInstead
        implementation("com.squareup.okhttp3:okhttp:4.9.3") // Thư viện OkHttp

        // Lưu ý: Đối với việc phân tích XML, bạn không cần thư viện ngoài nếu dùng XmlPullParser
        // Nếu muốn phân tích JSON dễ dàng hơn, bạn có thể thêm Gson (tùy chọn)
        // implementation("com.google.code.gson:gson:2.8.9")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}