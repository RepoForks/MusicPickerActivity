package com.finalweek10.android.musicpickeractivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.finalweek10.android.musicpicker.ringtone.MusicPickerActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_MUSIC_CODE = 57;

    private TextView mTitleView;
    private TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleView = (TextView) findViewById(R.id.title_text);
        mContentView = (TextView) findViewById(R.id.content_text);

        mTitleView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent =
                        MusicPickerActivity.createRingtonePickerIntent(MainActivity.this);
                intent.putExtra(MusicPickerActivity.EXTRA_PREVIEW_STREAM_TYPE,
                        AudioManager.STREAM_MUSIC);
                intent.putExtra(MusicPickerActivity.EXTRA_PREVIEW_AUDIO_ATTRIBUTES,
                        new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build());
                intent.putExtra(MusicPickerActivity.EXTRA_RINGTONE_URI,
                        Uri.parse(mContentView.getText().toString()));
                startActivityForResult(intent, REQUEST_MUSIC_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_MUSIC_CODE) {
                mTitleView.setText(
                        data.getStringExtra(MusicPickerActivity.EXTRA_SELECTED_NAME));
                mContentView.setText(data.getData().toString());
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
