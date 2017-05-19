# MusicPickerActivity
MusicPickerActivity provides a better way to select system default ringtones and other local music.
This library is separated and modified from [AOSP DeskClock](https://android.googlesource.com/platform/packages/apps/DeskClock/).
<h1 align="center">
<img src="/art/preview.gif" width="280" height="498" alt="Preview GIF"/>
<br/>    MusicPickerActivity
</h1>

#### Minimal API Level: 14
When devices is equal to or higher than API Level 19(KitKat), this library uses [ACTION_OPEN_DOCUMENT](https://developer.android.com/reference/android/content/Intent.html#ACTION_OPEN_DOCUMENT) to retrieve local audio files.
When devices is lower than KitKat, this library starts another activity(LocalMusicPickerActivity) to select.

## Usage
Step 1. Add the JitPack repository to your build file
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```
dependencies {
    ...
    compile 'com.github.ykAR6Bqy5DeG:MusicPickerActivity:1.0.3'
}
```
Step 3. Add activity style to styles.xml
Parent is **RingtonePickerTheme** and define colorPrimary and colorAccent here.
```
<style name="MyRingtonePickTheme" parent="RingtonePickerTheme">
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
    <item name="android:windowBackground">@color/colorPrimaryDark</item>
</style>
```
Step 4. Add activity to manifest
```
<activity
    android:name="com.finalweek10.android.musicpicker.ringtone.MusicPickerActivity"
    android:excludeFromRecents="true"
    android:taskAffinity=""
    android:theme="@style/AppTheme.MyRingtonePickTheme"/>

<activity
    android:name="com.finalweek10.android.musicpicker.ringtone.LocalMusicPickerActivity"
    android:excludeFromRecents="true"
    android:taskAffinity=""
    android:theme="@style/MyRingtonePickTheme"/>
```
Step 5. Select music and have fun!
```
startActivityForResult(MusicPickerActivity.createRingtonePickerIntent(MainActivity.this), REQUEST_MUSIC_CODE);
```
#### Check sample to explore all possible tweaks.
## Optional Values
### Extra values
| Keys          | Type           | Meaning  |
| ------------- |:-------------|:-----|
| EXTRA_TITLE      | string resource(int) | the resource id to the title of this activity |
| EXTRA_RINGTONE_URI      | Uri      |  the selected ringtone |
| EXTRA_DEFAULT_RINGTONE_URI | Uri      | your app's default ringtone URI |
| EXTRA_DEFAULT_RINGTONE_NAME | string resource(int)      | your app's default ringtone name |
| EXTRA_PREVIEW_STREAM_TYPE | stream type(int)      | the stream type of preview musics |
### Return values
| Keys          | Type           | Meaning  |
| ------------- |:-------------|:-----|
| EXTRA_SELECTED_NAME | String      | the name of user selecting music  |
| intent.getData() | Uri      | the uri of user selecting music  |
### Custom Style
Customize activity color using styles:

android:windowBackground: defines activity's background color

colorAccent: defines the color of "Your sounds", "Device sounds" and the check arrow.
<h1 align="center">
<img src="/art/screen.png" width="280" height="498" alt="Activity Screenshot"/>
</h1>

## Permissions
READ_EXTERNAL_STORAGE is used when devices is lower than KitKat.
Since this library doesn't know if there is a way to only declare a permission when API Level is bigger than 19,
he just include that permission in his AndroidManifest.xml.

## Test
This library works fine with my physical KitKat and Lollipop devices.
And while using API Level 15 and 16 emulators, selecting system default music works fine
but I cannot test selecting local music because of a possible emulator bug(Cannot recognize SD card on those emulators).
So if you find something goes wrong, just post an issue or use any tool to tell me. :)

## Update
#### 1.0.3
Makes project resources private
#### 1.0.2
Fixes shared preference lost bug on Android N
#### 1.0.1
1. Adds support for devices lower than API Level 19
2. Adds six translations
3. Learns how to publish a library and apply it...

## TODO
1. Add more translations(Current Available Languages: German, Spanish, French, Japanese, Simplified Chinese and Traditional Chinese.)

## License
MusicPickerActivity is licensed under the [MIT license](LICENSE).

DeskClock
```
  Copyright (C) 2016 The Android Open Source Project

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
