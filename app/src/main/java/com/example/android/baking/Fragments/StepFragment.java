package com.example.android.baking.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class StepFragment extends Fragment implements ExoPlayer.EventListener {
    @BindView(R.id.playerView)
    SimpleExoPlayerView simpleExoPlayer;
    @BindView(R.id.textView_StepFragment)
    TextView mStepDescription;
    @BindView(R.id.Next_fragmentStep)
    Button mNextButton;
    @BindView(R.id.back_fragmentStep)
    Button mBackButton;
    private SimpleExoPlayer exoPlayer;

    private final String TAG = "stepFragment";
    private String description;
    private String URL;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private final String KEY = "currentPosition";
    private SkipToNextPosition skipToNextPosition;
    private boolean size;
    private int count;
    private MediaSource mediaSource;
    private long chceckIfWork = -1;
    private ClickPreviousButton mPrevius;

    public StepFragment() {
        // Required empty public constructor
    }
    public interface ClickPreviousButton{
        void clickPreviousButton();
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());


    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }


    public interface SkipToNextPosition {
        void startNextStepItem(int position);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            skipToNextPosition = (SkipToNextPosition) context;
            mPrevius = (ClickPreviousButton) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString()
                    + " must implement WhenIngredientsViewIsClicked");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, view);

        if (URL != null) {
            if (!checkNet()) {
                Toast.makeText(getContext(), "Check Network", Toast.LENGTH_SHORT).show();
            }
            initializeMediaSession();
            if (savedInstanceState == null) {
                initializeExpoPlayer(0);


            } else {
                long getPosition = savedInstanceState.getLong(KEY);
                exoPlayer.release();
                initializeExpoPlayer(getPosition);

            }
        } else {
            simpleExoPlayer.setVisibility(View.GONE);
        }


        mStepDescription.setText(description);

        if (size) {
            mNextButton.setVisibility(View.VISIBLE);
        }
        if(getResources().getConfiguration().screenWidthDp >= 600){
            mBackButton.setVisibility(View.GONE);
        }


        return view;
    }

    private boolean checkNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }


    public void getStepData(String getDescription, String getURL, boolean sizeOfArray, int getCount) {
        count = getCount;
        size = sizeOfArray;
        description = getDescription;
        URL = getURL;
    }

    private void initializeExpoPlayer(long currentPosition) {
        try {
            Uri uri = Uri.parse(URL);


            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("BakingApp");

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);


            if (currentPosition > 0) {
                exoPlayer.seekTo(currentPosition);
            }
            exoPlayer.addListener(this);


        } catch (Exception e) {
            Log.i(TAG, "initializeExpoPlayer: something wrong" + e.toString());
        }
    }


    public void releasePayer() {
        exoPlayer.stop();
        exoPlayer.release();


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (URL != null) {
            outState.putLong(KEY, simpleExoPlayer.getPlayer().getCurrentPosition());
        }
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_FAST_FORWARD | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new CustomSessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }


    @OnClick(R.id.Next_fragmentStep)
    public void clickOnButton() {
        if (URL != null) {
            releasePayer();
            mMediaSession.setActive(false);
        }
        skipToNextPosition.startNextStepItem(count + 1);


    }
    @OnClick(R.id.back_fragmentStep)
    public void clickPreviousButton(){
        mPrevius.clickPreviousButton();

    }



    @Override
    public void onStop() {
        super.onStop();
        if (URL != null) {
            chceckIfWork = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (URL != null) {
            if (chceckIfWork != -1) {
                exoPlayer.seekTo(chceckIfWork);

            }
            simpleExoPlayer.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        }

    }


    private class CustomSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }

    }

    public static class MediaReceiver extends BroadcastReceiver {


        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }
}
