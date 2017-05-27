# MusicPickerActivity
MusicPickerActivity provides a better way to select system default ringtones and other local music.
This library is separated and modified from [AOSP DeskClock](https://android.googlesource.com/platform/packages/apps/DeskClock/).
<h1 align="center">
<img src="/art/preview.gif" width="280" height="498" alt="Preview GIF"/>
<br/>    MusicPickerActivity
</h1>

#### Minimal API Level: 14
For Android 19+(includes 19), this library uses [ACTION_OPEN_DOCUMENT](https://developer.android.com/reference/android/content/Intent.html#ACTION_OPEN_DOCUMENT) to retrieve local audio files.
For Android 19-, this library starts another activity(LocalMusicPickerActivity) to select.

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
    compile 'com.github.ykAR6Bqy5DeG:MusicPickerActivity:0.0.3'
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
Step 4. Add activities to manifest
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
startActivityForResult(MusicPickerActivity.createRingtonePickerIntent(MainActivity.this), 0);
```
Check [sample activity](https://github.com/ykAR6Bqy5DeG/MusicPickerActivity/tree/master/sample/src/main/java/com/finalweek10/android/musicpickeractivity/MainActivity.java) to explore all possible tweaks.
## Optional Values
### Extra values
| Keys          | Type           |Default value| Meaning  |
| ------------- |:-------------|:--------|:-----|
| EXTRA_TITLE| string resource(int) |"Music Picker"| the resource id to the title of this activity |
| EXTRA_RINGTONE_URI| Uri|Uri.EMPTY|the selected ringtone|
| EXTRA_DEFAULT_RINGTONE_URI | Uri|Uri.EMPTY| your app's default ringtone URI |
| EXTRA_DEFAULT_RINGTONE_NAME |string resource(int)|R.string.default_alarm_ringtone_title| your app's default ringtone name |
| EXTRA_PREVIEW_STREAM_TYPE |stream type(int)|AudioManager.STREAM_ALARM| the stream type of preview musics |
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
READ_EXTERNAL_STORAGE is used for Android 19-(excludes 19)

Since this library doesn't know if there is a way to only declare a permission when API Level is lower than 19,
he just include that permission in his AndroidManifest.xml.

## Update
->0.0.3 Use a nicer fast scroller and remove unnecessary dependencies and classes

## TODO
Add more translations(Current Available Languages: English, German, Spanish, French, Japanese, Simplified Chinese and Traditional Chinese.)

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
