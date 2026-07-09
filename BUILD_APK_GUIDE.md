# Build APK Guide for Umaa App

## Quick Start - Build APK

### Prerequisites
- Android Studio installed
- Java 11+ installed
- `.env` file created with `GEMINI_API_KEY=<your-key>`

### Method 1: Using Android Studio UI (Easiest)

1. **Open Android Studio**
   - File → Open → Select Umaa project directory
   - Wait for Gradle sync to complete

2. **Build Menu**
   - Click `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - Wait for build to complete (2-3 minutes)

3. **Locate APK**
   - Success message shows file location
   - Typically: `app/build/outputs/apk/debug/app-debug.apk`

4. **Transfer to Device**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

---

### Method 2: Using Gradle Command Line

```bash
# Navigate to project directory
cd /path/to/Umaa

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing config)
./gradlew assembleRelease

# APK output location
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

---

### Method 3: Build Release APK (Production)

#### Step 1: Remove Debug Signing Config
Edit `app/build.gradle.kts`:
```kotlin
buildTypes {
    release {
        isCrunchPngs = false
        isMinifyEnabled = false
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        signingConfig = signingConfigs.getByName("release")  // Keep this
    }
    debug {
        // Remove this line if present: signingConfig = signingConfigs.getByName("debugConfig")
    }
}
```

#### Step 2: Set Environment Variables (Optional)
```bash
export KEYSTORE_PATH=/path/to/your/keystore.jks
export STORE_PASSWORD=your_store_password
export KEY_PASSWORD=your_key_password
```

#### Step 3: Build Release
```bash
./gradlew assembleRelease
```

Output: `app/build/outputs/apk/release/app-release.apk`

---

## Install APK on Device/Emulator

### Via ADB (Android Debug Bridge)

```bash
# List connected devices
adb devices

# Install debug APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Install and launch
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.example/.MainActivity

# Clear app data before reinstalling
adb uninstall com.example
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Via Android Studio
1. Build → Select Run/Debug Configuration
2. Click Run (green play button)
3. Select target device/emulator

---

## Troubleshooting

### Build Fails with "Signing Config Not Found"
**Solution:** 
- Create `debug.keystore` in project root, OR
- Comment out `signingConfig` in `buildTypes.debug`

### "GEMINI_API_KEY Not Found"
**Solution:**
- Create `.env` file in project root
- Add: `GEMINI_API_KEY=your_actual_key`
- Run `./gradlew clean assembleDebug`

### APK Installation Fails
```bash
# Try uninstall + reinstall
adb uninstall com.example
adb install app/build/outputs/apk/debug/app-debug.apk
```

### "Gradle Sync Failed"
**Solution:**
- File → Invalidate Caches → Restart
- Terminal: `./gradlew clean build`

---

## APK Details

| Property | Value |
|----------|-------|
| Package Name | `com.example` |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 (Android 15) |
| Arch Support | arm64-v8a, armeabi-v7a, x86, x86_64 |
| Size (Debug) | ~50-80 MB |
| Size (Release) | ~30-50 MB |

---

## After Installation

1. **Grant Permissions** (if prompted)
   - Allow camera, storage, etc.

2. **Setup API Key**
   - Open app
   - Go to Settings
   - Without API key, Umaa uses offline mode

3. **Start Chatting**
   - Chat tab → Type message
   - Umaa responds with offline responses or via Gemini

---

## Share APK

```bash
# Copy APK to desktop for sharing
cp app/build/outputs/apk/debug/app-debug.apk ~/Desktop/umaa-debug.apk

# Get APK size
du -h app/build/outputs/apk/debug/app-debug.apk
```

---

## Advanced: Build Bundle for Play Store

```bash
# Build Android App Bundle (AAB)
./gradlew bundleRelease

# Output: app/build/outputs/bundle/release/app-release.aab

# Use Android App Bundle Tool to generate universal APK
bundletool build-apks \
  --bundle=app/build/outputs/bundle/release/app-release.aab \
  --output=app.apks
```

---

**Done! Your APK is ready to install. 🚀📦**
