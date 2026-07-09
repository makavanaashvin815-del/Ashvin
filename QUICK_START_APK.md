# Quick Start Guide - Build & Test Umaa APK

## 📱 Build APK in 3 Steps

### Step 1: Setup Environment
```bash
# Navigate to project
cd Umaa

# Create .env file with your API key
echo "GEMINI_API_KEY=your_key_here" > .env
```

### Step 2: Choose Your Build Method

#### **Option A: Using Build Script (Easiest)**

**macOS / Linux:**
```bash
chmod +x build_apk.sh
./build_apk.sh
```

**Windows:**
```bash
build_apk.bat
```

#### **Option B: Direct Gradle Command**

**Debug APK:**
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

**Release APK:**
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

#### **Option C: Android Studio UI**
1. Click `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
2. Wait for completion
3. APK location shown in notification

---

### Step 3: Install & Test

**Via ADB:**
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

**Via Android Studio:**
- Click green `Run` button (▶️)
- Select target device

---

## 📊 Build Times & Sizes

| Type | Time | Size | Use Case |
|------|------|------|----------|
| Debug | ~2-3 min | 60-80 MB | Development & Testing |
| Release | ~3-5 min | 30-50 MB | Production / Play Store |

---

## 🧪 First Launch Test Checklist

After installing APK:

- [ ] App launches without crash
- [ ] Chat tab loads default greeting
- [ ] Can type message in chat input
- [ ] Can set study goal
- [ ] Can add study task
- [ ] Task completion animation works
- [ ] Study mode can be activated
- [ ] Settings page loads
- [ ] Can change nickname
- [ ] Can wipe chat history

---

## 📦 Share APK

```bash
# Copy to Desktop
cp app/build/outputs/apk/debug/app-debug.apk ~/Desktop/

# Get size
du -h app/build/outputs/apk/debug/app-debug.apk
# Output: 65M    app-debug.apk

# Share via email/cloud
# Send Desktop/app-debug.apk to team
```

---

## 🐛 Troubleshooting Build

| Error | Solution |
|-------|----------|
| `GEMINI_API_KEY not found` | Create `.env` file with key |
| `Gradle sync failed` | Run `./gradlew clean` |
| `Build timeout` | Increase Gradle heap: `-Xmx4g` |
| `Signing config not found` | Comment out debug signing in `build.gradle.kts` |

---

## 🚀 Next Steps After APK

1. **Test on Real Device** - Install on Android phone
2. **Test on Emulator** - Use Android Studio's built-in emulator
3. **Collect Feedback** - Share with beta testers
4. **Prepare for Play Store** - Use release APK
5. **Upload to Play Store** - Once stable

---

**Build status:** ✅ Ready to deploy
