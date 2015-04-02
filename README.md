## How to integrate TapForTap for Android

- Download the TapForTap SDK (single .aar file)

- Create an Android Project in Android Studio

- In your app's build.gradle file (not the top-level project build.gradle, the application module's build.gradle), in the list of dependencies, add compile statements for the latest Android Support library and the latest Google Play Services library. Sync Gradle files. At the time this was written, those statements looked like this:

```
compile 'com.android.support:appcompat-v7:22.0.0'
compile 'com.google.android.gms:play-services:7.0.0'
```

- But the version numbers have likely changed by now. So please check these pages first:

http://developer.android.com/google/play-services/setup.html
http://developer.android.com/tools/support-library/features.html#v7-appcompat

- Go to File -> New Module

- Select the "Import .JAR or .AAR Package" option from the list under "More Modules", click "Next"

- Use the file menu to select the TapForTap SDK, Android Studio should fill in both text fields.

- In your app's build.gradle file, in the list of dependencies, add a line like this: 

```
compile project(':TapForTap')
```

- Sync Gradle Files and attempt to compile the project. 

- This may cause an error similar to this: `Execution failed for task ':app:packageDebug' > Duplicate files copied in APK [some meta file]`

- If so, the compiler will recommend adding some code to the android { } block of your build.gradle file. Here is the code you'll need to add:

```
packagingOptions {
    exclude 'META-INF/LICENSE.txt'
    exclude 'META-INF/NOTICE.txt'
}
```

## Permissions and Metadata

- **No extra permissions are necessary**, but building the .aar file into your project will automatically add the following permissions:

```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />                
<uses-permission android:name="android.permission.ACCESS_COARSE_UPDATES" /> 
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.GET_TASKS" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION"/>    
<uses-feature android:glEsVersion="0x00020000" android:required="true"/>
<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<permission android:name="com.tapfortap.sdk.permission.C2D_MESSAGE" android:protectionLevel="signature" />
<uses-permission android:name="com.tapfortap.sdk.permission.C2D_MESSAGE" />
```

- Set `com.tapfortap.API_KEY` in your AndroidManifest.xml to your Tap for Tap API Key, inside the `<application>` tag:

```xml
<meta-data android:name="com.tapfortap.API_KEY" android:value="3d323e6d58c83e06dba2547ec54f8afc"/>
```

- Set `mediabrixAppID` in your AndroidManifest.xml to the MediaBrix app ID that was provided to you:

```xml
<meta-data android:name="mediabrixAppID" android:value="qXDpTFlISq"/>
```

- Set `mediabrixProperty` in your AndroidManifest.xml to the MediaBrix property that was provided to you

```xml
<meta-data android:name="mediabrixProperty" android:value="pretio_pretioqa_mobile"/>
```

## Initialization

```java
TapForTap.setEnvironment("production");
TapForTap.initialize(this, "3d323e6d58c83e06dba2547ec54f8afc");
```

## Showing Interstitials and Banners

```java
// Create an InterstitialListener
Interstitial.InterstitialListener interstitialListener = new Interstitial.InterstitialListener() {
    @Override
    public void interstitialDidReceiveAd(Interstitial interstitial) {
        Log.i("MainActivity", "interstitialDidReceiveAd");
        interstitial.show();
    }

    @Override
    public void interstitialDidFail(Interstitial interstitial, String reason, Throwable throwable) {
        Log.i("MainActivity", "interstitialDidFail because: " + reason);
    }

    @Override
    public void interstitialDidShow(Interstitial interstitial) {
        Log.i("MainActivity", "interstitialDidShow");
    }

    @Override
    public void interstitialWasTapped(Interstitial interstitial) {
        Log.i("MainActivity", "interstitialWasTapped");
    }

    @Override
    public void interstitialWasDismissed(Interstitial interstitial) {
        Log.i("MainActivity", "interstitialWasDismissed");
    }

    @Override
    public void interstitialAdWasRewarded(Interstitial interstitial) {
        Log.i("MainActivity", "interstitialAdWasRewarded");
    }
};


// Show a break interstitial
Interstitial.loadBreakInterstitial(this, interstitialListener);

// Show an achievement interstitial
Interstitial.loadAchievementInterstitial(this, "description", "rewardDescription", rewardIconUrl, interstitialListener);

// Show a rescue interstitial
Interstitial.loadRescueInterstitial(this, "title", "brandingText", "enticementText", "rewardDescription", rewardIconUrl, "optInButtonText", interstitialListener); 
```
