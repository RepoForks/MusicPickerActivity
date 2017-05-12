package com.finalweek10.android.musicpicker.ringtone;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import com.finalweek10.android.musicpicker.R;
import com.finalweek10.android.musicpicker.util.ItemAdapter;
import com.finalweek10.android.musicpicker.util.RingtonePreviewKlaxon;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import static com.finalweek10.android.musicpicker.ringtone.RingtoneViewHolder.VIEW_TYPE_CUSTOM_SOUND;

/**
 * This activity presents all device's external musics.
 * This activity is used when users' device is lower than
 * {@link android.os.Build.VERSION_CODES#KITKAT}
 * when {@link Intent#ACTION_OPEN_DOCUMENT} is not available.
 */
public class LocalMusicPickerActivity extends BaseMusicActivity
        implements LoaderManager.LoaderCallbacks<List<RingtoneHolder>> {

    /**
     * Stores the set of ItemHolders that wrap the selectable ringtones.
     */
    private FastScrollAdapter mRingtoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);

        mRecyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final LayoutInflater inflater = getLayoutInflater();
        final ItemAdapter.OnItemClickedListener listener = new ItemClickWatcher();
        final ItemAdapter.ItemViewHolder.Factory ringtoneFactory =
                new RingtoneViewHolder.Factory(inflater);
        mRingtoneAdapter = new FastScrollAdapter();
        mRingtoneAdapter.withViewTypes(ringtoneFactory, listener, VIEW_TYPE_CUSTOM_SOUND);

        mRecyclerView.setAdapter(mRingtoneAdapter);

        getLoaderManager().initLoader(0 /* id */, null /* args */, this /* callback */);
    }

    @Override
    public void finish() {
        if (mSelectedRingtoneUri != null) {
            RingtoneHolder holder = getSelectedRingtoneHolder();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_SELECTED_NAME, holder.getName())
                    .setData(holder.getUri());
            setResult(RESULT_OK, resultIntent);
        }
        super.finish();
    }

    @Override
    public Loader<List<RingtoneHolder>> onCreateLoader(int id, Bundle args) {
        return new LocalMusicLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<RingtoneHolder>> loader,
                               List<RingtoneHolder> data) {
        // Update the adapter with fresh data.
        mRingtoneAdapter.setItems(data);

        // Clear the selection since it does not exist in the data.
        RingtonePreviewKlaxon.stop(this);
        mSelectedRingtoneUri = null;
        mIsPlaying = false;
    }

    @Override
    public void onLoaderReset(Loader<List<RingtoneHolder>> loader) {
    }

    @Override
    protected RingtoneHolder getSelectedRingtoneHolder() {
        return getRingtoneHolder(mSelectedRingtoneUri);
    }

    private RingtoneHolder getRingtoneHolder(Uri uri) {
        for (ItemAdapter.ItemHolder<Uri> itemHolder : mRingtoneAdapter.getItems()) {
            if (itemHolder instanceof RingtoneHolder) {
                final RingtoneHolder ringtoneHolder = (RingtoneHolder) itemHolder;
                if (ringtoneHolder.getUri().equals(uri)) {
                    return ringtoneHolder;
                }
            }
        }

        return null;
    }

    private class FastScrollAdapter extends ItemAdapter<RingtoneHolder>
            implements FastScrollRecyclerView.SectionedAdapter {
        @NonNull
        @Override
        public String getSectionName(int position) {
            String title = getItemAt(position).getName();
            return title.length() > 0 ? title.substring(0, 1) : "";
        }
    }

    /**
     * the same click-to-preview action as
     * {@link MusicPickerActivity.ItemClickWatcher}.CLICK_NORMAL part
     */
    private class ItemClickWatcher implements ItemAdapter.OnItemClickedListener {
        @Override
        public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder, int id) {
            switch (id) {
                case RingtoneViewHolder.CLICK_NORMAL:
                    final RingtoneHolder oldSelection = getSelectedRingtoneHolder();
                    final RingtoneHolder newSelection = (RingtoneHolder) viewHolder.getItemHolder();

                    // Tapping the existing selection toggles playback of the ringtone.
                    if (oldSelection == newSelection) {
                        if (newSelection.isPlaying()) {
                            stopPlayingRingtone(newSelection, false);
                        } else {
                            startPlayingRingtone(newSelection);
                        }
                    } else {
                        // Tapping a new selection changes the selection and playback.
                        stopPlayingRingtone(oldSelection, true);
                        startPlayingRingtone(newSelection);
                    }
                    break;
            }
        }
    }
}
