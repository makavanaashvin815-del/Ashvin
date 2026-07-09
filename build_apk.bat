@echo off
REM Umaa APK Build Script for Windows
REM Automates debug and release APK building

echo.
echo 🚀 Umaa APK Builder
echo ====================
echo.

REM Check if .env file exists
if not exist ".env" (
    echo ❌ Error: .env file not found!
    echo Please create .env file with GEMINI_API_KEY
    echo Example: GEMINI_API_KEY=your_actual_key
    pause
    exit /b 1
)

REM Menu
echo Select build type:
echo 1) Debug APK (recommended for testing)
echo 2) Release APK (for production)
echo 3) Both
echo.
set /p choice="Enter choice [1-3]: "

if "%choice%"=="1" (
    echo Building Debug APK...
    call gradlew.bat clean assembleDebug
    set APK_PATH=app\build\outputs\apk\debug\app-debug.apk
    echo ✅ Debug APK built successfully!
    echo APK Location: %APK_PATH%
    dir %APK_PATH%
    echo.
    set /p install="Install on device? (y/n): "
    if "%install%"=="y" (
        echo Available devices:
        adb devices
        adb install -r %APK_PATH%
        echo ✅ APK installed!
    )
) else if "%choice%"=="2" (
    echo Building Release APK...
    call gradlew.bat clean assembleRelease
    set APK_PATH=app\build\outputs\apk\release\app-release.apk
    echo ✅ Release APK built successfully!
    echo APK Location: %APK_PATH%
    dir %APK_PATH%
) else if "%choice%"=="3" (
    echo Building both Debug and Release APKs...
    echo.
    echo Building Debug...
    call gradlew.bat clean assembleDebug
    set DEBUG_APK=app\build\outputs\apk\debug\app-debug.apk
    echo ✅ Debug APK:
    dir %DEBUG_APK%
    echo.
    echo Building Release...
    call gradlew.bat assembleRelease
    set RELEASE_APK=app\build\outputs\apk\release\app-release.apk
    echo ✅ Release APK:
    dir %RELEASE_APK%
) else (
    echo ❌ Invalid choice!
    pause
    exit /b 1
)

echo.
echo ================== Build Summary ==================
echo Project: Umaa - AI Study Bestie
echo Package: com.example
echo.
echo Next steps:
echo 1. Test on emulator/device
echo 2. Share APK with team
echo 3. Upload to Play Store (release only)
echo.
echo ✨ Build complete!
echo.
pause
