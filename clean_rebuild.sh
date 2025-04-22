#!/bin/bash
rm -rf .gradle
rm -rf app/build
rm -rf build
./gradlew clean
./gradlew assembleDebug 