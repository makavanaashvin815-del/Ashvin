# 📱 Umaa Android App - Build & Get APK File

## Quick Summary
- **App Name:** Umaa (AI Study Bestie)
- **Type:** Android Native App (Jetpack Compose)
- **Min SDK:** Android 7.0 (API 24)
- **Target SDK:** Android 15 (API 36)
- **Language:** Kotlin
- **Architecture:** MVVM + Room Database + Gemini API

---

## 🚀 Get APK in 5 Minutes

### **Step 1: Clone/Download Project**
```bash
git clone https://github.com/makavanaashvin815-del/Ashvin.git
cd Ashvin
```

### **Step 2: Setup API Key**
```bash
# Create .env file in project root
echo "GEMINI_API_KEY=your_gemini_api_key" > .env
```

Get free API key: https://ai.google.dev/

### **Step 3: Build APK**

**Using Gradle (Terminal/CMD):**
```bash
# Debug APK (for testing)
./gradlew assembleDebug

# OR Release APK (for Play Store)
./gradlew assembleRelease
```

**Using Android Studio:**
1. Open project in Android Studio
2. Click `Build` menu → `Build Bundle(s) / APK(s)` → `Build APK(s)`
3. Wait 2-3 minutes

### **Step 4: Locate APK**

Debug APK: `app/build/outputs/apk/debug/app-debug.apk` (~65 MB)

Release APK: `app/build/outputs/apk/release/app-release.apk` (~35 MB)

### **Step 5: Install on Device**

```bash
# Connect Android phone via USB
# Enable USB Debugging on phone

# Install APK
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Or use Android Studio - Press Shift+F10
```

---

## 📦 APK Details

| Aspect | Details |
|--------|---------|
| **Package Name** | com.example |
| **App Name** | Umaa |
| **Size (Debug)** | ~65 MB |
| **Size (Release)** | ~35 MB |
| **Min Android Version** | 7.0 (API 24) |
| **Target Android** | 15 (API 36) |
| **Supports** | arm64-v8a, armeabi-v7a, x86, x86_64 |

---

## ✨ Features in APK

### Chat Tab 💬
- Talk to Umaa in Hinglish
- Real-time AI responses (with offline fallback)
- Google Search integration for verified sources
- Conversation history

### Study Board 📚
- Set/Track study goals
- View statistics (streak, focus hours, sessions)
- Daily task management
- Study mode activation

### Study Tasks 📝
- Add daily study tasks
- Mark as complete (animated)
- Auto-celebrate achievements
- Delete tasks

### Focus Mode 🔒
- Full-screen immersive study session
- Motivational messages from Umaa
- Timer tracking
- Ziddi (stubborn) best friend mode

### Settings ⚙️
- Change nickname
- Read motivation quotes
- Wipe chat history
- Manage preferences

---

## 🧪 Test After Installation

After installing APK on Android device:

```
✅ App launches without crash
✅ Can chat with Umaa
✅ Can set study goals
✅ Can add/complete tasks
✅ Study mode works
✅ Settings are accessible
```

---

## 📋 System Requirements

**To Build:**
- Java 11+
- Android SDK (API 24-36)
- 2GB+ RAM
- Gradle 8.x

**To Run:**
- Android 7.0+ device
- Internet connection (for Gemini API)
- ~200MB free storage

---

## 🔗 Important Files

```
Ashvin/
├── app/src/main/
│   ├── java/com/example/
│   │   ├── MainActivity.kt          # Entry point
│   │   ├── ui/screens/BentoHomeScreen.kt  # Main UI
│   │   ├── ui/viewmodel/HomeViewModel.kt  # State management
│   │   └── data/api/GeminiClient.kt       # Gemini integration
│   └── res/                         # Resources, colors, themes
├── build.gradle.kts                 # Build config
├── .env                             # API key (create this)
└── gradlew                          # Gradle wrapper
```

---

## 🛠️ Troubleshooting

| Problem | Solution |
|---------|----------|
| Build fails | Run `./gradlew clean` first |
| No .env file | Create `.env` with `GEMINI_API_KEY=your_key` |
| APK won't install | Uninstall old version: `adb uninstall com.example` |
| "Gradle sync failed" | File → Invalidate Caches → Restart |
| App crashes on launch | Check Java version: `java -version` |

---

## 📊 Build Output

After successful build, you'll see:
```
✅ BUILD SUCCESSFUL
📁 APK: app/build/outputs/apk/debug/app-debug.apk
📊 Size: 65M
```

---

## 🎯 Next Steps

1. ✅ Build APK
2. ✅ Install on Android device
3. ✅ Test all features
4. ✅ Share with friends
5. ✅ (Optional) Upload to Play Store

---

**Build Status: Ready to deploy! 🚀**

Need help? Check BUILD_APK_GUIDE.md for detailed instructions.
