package com.finalweek10.android.musicpicker.ringtone;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.finalweek10.android.musicpicker.data.CustomRingtone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created on 2017/5/10.
 * Hello, friend.
 */


class LocalMusicLoader extends AsyncTaskLoader<List<RingtoneHolder>>  {

    LocalMusicLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<RingtoneHolder> loadInBackground() {
        final List<RingtoneHolder> itemHolders = new ArrayList<>();

        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int titleColumn = cursor.getColumnIndex(
                        android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = cursor.getColumnIndex(
                        android.provider.MediaStore.Audio.Media._ID);
                do {
                    long thisId = cursor.getLong(idColumn);
                    String thisTitle = cursor.getString(titleColumn);
                    CustomRingtone customRingtone = new CustomRingtone(thisId,
                            ContentUris.withAppendedId(
                                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                    thisId),
                            thisTitle, true);
                    itemHolders.add(new CustomRingtoneHolder(customRingtone));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        Collections.sort(itemHolders, new Comparator<RingtoneHolder>() {
            @Override
            public int compare(RingtoneHolder o1, RingtoneHolder o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
            }
        });

        return itemHolders;
    }
}
