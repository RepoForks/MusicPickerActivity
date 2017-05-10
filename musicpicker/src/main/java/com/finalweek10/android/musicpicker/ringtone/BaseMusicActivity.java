package com.finalweek10.android.musicpicker.ringtone;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.finalweek10.android.musicpicker.R;
import com.finalweek10.android.musicpicker.actionbarmenu.NavUpMenuItemController;
import com.finalweek10.android.musicpicker.actionbarmenu.OptionsMenuManager;
import com.finalweek10.android.musicpicker.util.DropShadowController;
import com.finalweek10.android.musicpicker.util.RingtonePreviewKlaxon;

public abstract class BaseMusicActivity extends AppCompatActivity {

    /**
     * Key to an extra that identifies the selected ringtone.
     */
    public static final String EXTRA_RINGTONE_URI = "extra_ringtone_uri";

    /**
     * Key to an extra that defines the name of the selected music.
     */
    public static final String EXTRA_SELECTED_NAME = "extra_selected_title";

    /**
     * Key to an instance state value indicating if the selected ringtone is currently playing.
     */
    protected static final String STATE_KEY_PLAYING = "extra_is_playing";

    /**
     * The controller that shows the drop shadow when content is not scrolled to the top.
     */
    protected DropShadowController mDropShadowController;

    /**
     * Generates the items in the activity context menu.
     */
    protected OptionsMenuManager mOptionsMenuManager;

    /**
     * Displays a set of selectable ringtones.
     */
    protected RecyclerView mRecyclerView;

    /**
     * {@code true} indicates the {@link #mSelectedRingtoneUri} must be played after data load.
     */
    protected boolean mIsPlaying;

    /**
     * The uri of the ringtone to select after data is loaded.
     */
    protected Uri mSelectedRingtoneUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIsPlaying = savedInstanceState.getBoolean(STATE_KEY_PLAYING);
            mSelectedRingtoneUri = savedInstanceState.getParcelable(EXTRA_RINGTONE_URI);
        }

        mOptionsMenuManager = new OptionsMenuManager();
        mOptionsMenuManager.addMenuItemController(new NavUpMenuItemController(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenuManager.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mOptionsMenuManager.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mOptionsMenuManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final View dropShadow = findViewById(R.id.drop_shadow);
        mDropShadowController = new DropShadowController(dropShadow, mRecyclerView);
    }

    @Override
    protected void onPause() {
        mDropShadowController.stop();
        mDropShadowController = null;
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (!isChangingConfigurations()) {
            stopPlayingRingtone(getSelectedRingtoneHolder(), false);
        }
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_KEY_PLAYING, mIsPlaying);
        outState.putParcelable(EXTRA_RINGTONE_URI, mSelectedRingtoneUri);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected abstract RingtoneHolder getSelectedRingtoneHolder();

    /**
     * The given {@code ringtone} will be selected as a side-effect of playing the ringtone.
     *
     * @param ringtone the ringtone to be played
     */
    protected void startPlayingRingtone(RingtoneHolder ringtone) {
        if (!ringtone.isPlaying() && !ringtone.isSilent()) {
            RingtonePreviewKlaxon.start(getApplicationContext(), ringtone.getUri());
            ringtone.setPlaying(true);
            mIsPlaying = true;
        }
        if (!ringtone.isSelected()) {
            ringtone.setSelected(true);
            mSelectedRingtoneUri = ringtone.getUri();
        }
        ringtone.notifyItemChanged();
    }

    /**
     * @param ringtone the ringtone to stop playing
     * @param deselect {@code true} indicates the ringtone should also be deselected;
     *                 {@code false} indicates its selection state should remain unchanged
     */
    protected void stopPlayingRingtone(RingtoneHolder ringtone, boolean deselect) {
        if (ringtone == null) {
            return;
        }

        if (ringtone.isPlaying()) {
            RingtonePreviewKlaxon.stop(this);
            ringtone.setPlaying(false);
            mIsPlaying = false;
        }
        if (deselect && ringtone.isSelected()) {
            ringtone.setSelected(false);
            mSelectedRingtoneUri = null;
        }
        ringtone.notifyItemChanged();
    }
}
