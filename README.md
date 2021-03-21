# DEPRECATED: Syte Native Android SDK

This repository is now deprecated and is not recommended for use. There will be no maintanance and no response to any issues.

# Files

 - Syte native SDK - .aar
 - Demo application

# How To Use

 1. Generate .aar file: in 'sytesdk' run gradle 'assemble' and gradle 'publish'.
 2. Copy sytesdk-release.aar file from build directory (created by previous commands) to 'libs' directory in your project (create if not exists).
 3. Add in your build.gradle (module level) file the following line (if not exists):

    implementation fileTree(dir: 'libs', include: ['*.aar'])


# Account Credentials

In order to use this SDK, please contact Syte for your account ID and API key.     

# Use the SDK

    - Initialize the SDK:
    init(appContect, accountId, token)
    
    - Add external storage permission to AndroidManifest.xml:
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    
    - Set debug mode on/off:
    setDebugMode(boolean)
    
    - Modify config example:
    options: catalog, currency, gender, category
    modifyConfig(currency = "EUR")
    
    - Get bounds for selected image:
    getBoundsForImage(uri, callback)
    
    - Get bounds for image URL:
    getBoundsForImage(imageUrl, callback)
    
    - Get offers for bound:
    getOffers(offerUrl, callback)
