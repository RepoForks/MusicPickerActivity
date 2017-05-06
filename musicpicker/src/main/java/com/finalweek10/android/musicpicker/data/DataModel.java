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

package com.finalweek10.android.musicpicker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.List;

import static com.finalweek10.android.musicpicker.util.Utils.enforceMainLooper;
import static com.finalweek10.android.musicpicker.util.Utils.enforceNotMainLooper;

/**
 * All application-wide data is accessible through this singleton.
 */
public final class DataModel {
    private Context mContext;

    /** The model from which ringtone data are fetched. */
    private RingtoneModel mRingtoneModel;

    /**
     * Initializes the data model with the context and shared preferences to be used.
     */
    public DataModel(Context context, SharedPreferences prefs) {
        if (mContext != context) {
            mContext = context.getApplicationContext();
            mRingtoneModel = new RingtoneModel(mContext, prefs);
        }
    }

    //
    // UI
    //

    /**
     * @return the duration in milliseconds of short animations
     */
    public long getShortAnimationDuration() {
        enforceMainLooper();
        return mContext.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    //
    // Ringtones
    //

    /**
     * Ringtone titles are cached because loading them is expensive. This method
     * <strong>must</strong> be called on a background thread and is responsible for priming the
     * cache of ringtone titles to avoid later fetching titles on the main thread.
     */
    public void loadRingtoneTitles() {
        enforceNotMainLooper();
        mRingtoneModel.loadRingtoneTitles();
    }

    /**
     * Recheck the permission to read each custom ringtone.
     */
    public void loadRingtonePermissions() {
        enforceNotMainLooper();
        mRingtoneModel.loadRingtonePermissions();
    }

    /**
     * @param uri the uri of a ringtone
     * @return the title of the ringtone with the {@code uri}; {@code null} if it cannot be fetched
     */
    public String getRingtoneTitle(Uri uri) {
        enforceMainLooper();
        return mRingtoneModel.getRingtoneTitle(uri);
    }

    /**
     * @param uri the uri of an audio file to use as a ringtone
     * @param title the title of the audio content at the given {@code uri}
     * @return the ringtone instance created for the audio file
     */
    public CustomRingtone addCustomRingtone(Uri uri, String title) {
        enforceMainLooper();
        return mRingtoneModel.addCustomRingtone(uri, title);
    }

    /**
     * @param uri identifies the ringtone to remove
     */
    public void removeCustomRingtone(Uri uri) {
        enforceMainLooper();
        mRingtoneModel.removeCustomRingtone(uri);
    }

    /**
     * @return all available custom ringtones
     */
    public List<CustomRingtone> getCustomRingtones() {
        enforceMainLooper();
        return mRingtoneModel.getCustomRingtones();
    }
}
