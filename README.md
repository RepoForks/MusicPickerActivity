# MusicPickerActivity
Separated and modified from [AOSP DeskClock](https://android.googlesource.com/platform/packages/apps/DeskClock/),
MusicPickerActivity provides a better way to select system default ringtones and other local music.
<h1 align="center">
<img src="/art/preview.gif" width="280" height="498" alt="Preview GIF"/>
<br/>    MusicPickerActivity
</h1>

#### Minimal API Level: 19 (Because activity uses [ACTION_OPEN_DOCUMENT](https://developer.android.com/reference/android/content/Intent.html#ACTION_OPEN_DOCUMENT) to retrieve local device audios. I've been working on how to support lower API Level.)
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
    compile 'com.github.ykAR6Bqy5DeG:MusicPickerActivity:v1.0'
}
```
Step 3. Add activity style to styles.xml
Parent is **RingtonePickerTheme** and define colorPrimary and colorAccent here.
```
<style name="MyRingtonePickTheme" parent="RingtonePickerTheme">
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
```
Step 5. Select music and have fun!
```
startActivityForResult(MusicPickerActivity.createRingtonePickerIntent(MainActivity.this), REQUEST_MUSIC_CODE);
```
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

## TODO
1. Copy all translations in the AOSP DeskClock to values folder.
2. Support Lower API Level devices
