#!/bin/bash

rm syte-sdk-client.zip
mkdir "distro"

cp android-sdk/build/outputs/aar/android-sdk-debug.aar distro
cp -r javadoc distro
cp android-sdk/build/outputs/aar/android-sdk-release.aar distro
cp set-up-guide-zip.txt distro

zip -r syte-sdk-client.zip distro

rm -r javadoc
rm -r "distro"