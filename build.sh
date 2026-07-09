#!/bin/bash
# Umaa APK Builder - Automated Build Script
# This script builds the APK automatically

set -e

echo "🚀 Building Umaa APK..."
echo "======================"

# Check prerequisites
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 11+"
    exit 1
fi

if ! command -v gradle &> /dev/null && [ ! -f "gradlew" ]; then
    echo "❌ Gradle not found"
    exit 1
fi

# Check .env file
if [ ! -f ".env" ]; then
    echo "⚠️  .env file not found. Creating with placeholder..."
    cat > .env << EOF
GEMINI_API_KEY=placeholder_key
EOF
    echo "ℹ️  Please update .env with your actual GEMINI_API_KEY"
fi

# Build
echo "📦 Building debug APK..."
./gradlew clean assembleDebug -q

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    echo ""
    echo "✅ BUILD SUCCESSFUL!"
    echo "📁 APK Location: $(pwd)/$APK_PATH"
    echo "📊 Size: $(du -h "$APK_PATH" | cut -f1)"
    echo ""
    echo "Install command:"
    echo "  adb install -r \"$APK_PATH\""
    echo ""
    echo "Or run in Android Studio: Press Shift+F10"
else
    echo "❌ Build failed!"
    exit 1
fi
