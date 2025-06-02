plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.compose)
	alias(libs.plugins.ksp)
	alias(libs.plugins.protobuf)
}

android {
	namespace = "com.redkapetpty.shop2shopassesment"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.redkapetpty.shop2shopassesment"
		minSdk = 24
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
	kotlinOptions {
		jvmTarget = "11"
	}

	sourceSets["main"].java.srcDir("build/generated/source/proto/main/javalite")
	sourceSets["main"].kotlin.srcDir("build/generated/source/proto/main/javalite")
}

dependencies {

	// Jetpack Compose
	implementation(platform(libs.compose.bom))
	implementation(libs.compose.ui)
	implementation(libs.compose.material3)
	implementation(libs.compose.runtime)
	implementation(libs.compose.tooling.preview)
	testImplementation(libs.androidx.core)
	debugImplementation(libs.compose.tooling)

	// Lifecycle & Navigation
	implementation(libs.lifecycle.runtime.ktx)
	implementation(libs.lifecycle.viewmodel.cmp)
	implementation(libs.navigation.compose)

	// Coroutines
	implementation(libs.coroutines.android)

	// Koin DI
	implementation(libs.koin.android)
	implementation(libs.koin.compose)

	// Room (with KSP)
	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.room.compiler)

	coreLibraryDesugaring(libs.desugar.jdk)

	implementation(libs.material3.android)
	implementation(libs.androidx.material3)

	implementation(libs.datastore.core)
	implementation(libs.protobuf.javalite)

	// Networking
	implementation(libs.retrofit)
	implementation(libs.retrofit.moshi)
	implementation(platform(libs.okhttp.bom))
	implementation(libs.okhttp)
	implementation(libs.moshi)
	implementation(libs.androidx.core.ktx)
	testImplementation(libs.coroutines.test)
	testImplementation(libs.core.testing)
	testImplementation(libs.junit)
	testImplementation(libs.robolectric)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
}

protobuf {
	protoc { artifact = "com.google.protobuf:protoc:${libs.versions.protobufLite.get()}" }
	generateProtoTasks {
		all().forEach { task ->
			task.builtins {
				create("java") { option("lite") } // this will generate of javalite classes
			}
		}
	}
}