/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.finalweek10.android.musicpicker.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.AnyRes;
import android.support.v4.os.BuildCompat;
import android.view.View;

public class Utils {

    /**
     * {@link Uri} signifying the "silent" ringtone.
     */
    public static final Uri RINGTONE_SILENT = Uri.EMPTY;

    public static final Uri NO_RINGTONE_URI = Uri.EMPTY;

    public static void enforceMainLooper() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalAccessError("May only call from main thread.");
        }
    }

    public static void enforceNotMainLooper() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IllegalAccessError("May not call from main thread.");
        }
    }

    public static boolean isKitkatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * @return {@code true} if the device is {@link Build.VERSION_CODES#LOLLIPOP} or later
     */
    public static boolean isLOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * @return {@code true} if the device is {@link Build.VERSION_CODES#LOLLIPOP_MR1} or later
     */
    public static boolean isLMR1OrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * @return {@code true} if the device is {@link Build.VERSION_CODES#M} or later
     */
    public static boolean isMOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * @return {@code true} if the device is {@link Build.VERSION_CODES#N} or later
     */
    public static boolean isNOrLater() {
        return BuildCompat.isAtLeastN();
    }

    /**
     * @return {@code true} We can ACTION_OPEN_DOCUMENT to pick ringtones without permission.
     */
    public static boolean isUsingNewStorage() {
        return Utils.isKitkatOrLater();
//        return false;
    }


    /**
     * @param resourceId identifies an application resource
     * @return the Uri by which the application resource is accessed
     */
    public static Uri getResourceUri(Context context, @AnyRes int resourceId) {
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(context.getPackageName())
                .path(String.valueOf(resourceId))
                .build();
    }

    /**
     * @param view the scrollable view to test
     * @return {@code true} iff the {@code view} content is currently scrolled to the top
     */
    public static boolean isScrolledToTop(View view) {
        return !view.canScrollVertically(-1);
    }

    /**
     * @return milliseconds since boot, including time spent in sleep
     */
    public static long now() {
        return SystemClock.elapsedRealtime();
    }

    /**
     * Returns the default {@link SharedPreferences} instance from the underlying storage context.
     */
    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        context = context.getApplicationContext();
//        final Context storageContext;
        // When an app is running on N device and using default non-encrypted shared preference,
        // every time this block of commented code is ran, it will move those non-encrypted SP to
        // a secure area, causing a lost SP when using non-encrypted SP next time.
//        if (Utils.isNOrLater()) {
//            // All N devices have split storage areas. Migrate the existing preferences into the new
//            // device encrypted storage area if that has not yet occurred.
//            final String name = PreferenceManager.getDefaultSharedPreferencesName(context);
//            storageContext = context.createDeviceProtectedStorageContext();
//            if (!storageContext.moveSharedPreferencesFrom(context, name)) {
//                LogUtils.wtf("Failed to migrate shared preferences");
//            }
//        } else {
//        storageContext = context;
//        }
        return context.getSharedPreferences("music_picker_prefs", Context.MODE_PRIVATE);
    }
}