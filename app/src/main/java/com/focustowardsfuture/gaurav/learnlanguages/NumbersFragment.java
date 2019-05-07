package com.focustowardsfuture.gaurav.learnlanguages;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    MediaPlayer mMediaPlayer;


    AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //Pause PlayBack
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);// its from the beginning bcz our words are very short anyway
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //Resume playback
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView =inflater.inflate(R.layout.word_list,container,false);

        final ArrayList<Word> words = new ArrayList<>();
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        //Word w =new Word("one","lutti");
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter itemAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Word word = words.get(position);
                                                releaseMediaPlayer();


                                                // Request audio focus for playback
                                                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                                                        // Use the music stream.
                                                        AudioManager.STREAM_MUSIC,
                                                        // Request permanent focus.
                                                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                                                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                                                    // Start playback.
                                                    mMediaPlayer=MediaPlayer.create(getActivity(),word.getmAudioResourceID());
                                                    mMediaPlayer.start();
                                                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                                                }

                                            }
                                        }
        );


       return rootView;
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
        //Abandon audio focus
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
