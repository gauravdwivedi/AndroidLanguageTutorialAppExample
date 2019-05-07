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
public class FamilyFragment extends Fragment {
    MediaPlayer mediaPlayer;
    AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Pause PlayBack
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);// its from the beginning bcz our words are very short anyway
            } else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                //Resume playback
                mediaPlayer.start();
            } else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
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

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.word_list,container,false);
        final ArrayList<Word> words =new ArrayList<>();

        //Word w =new Word("one","lutti");
        words.add(new Word("Father","epe",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("Mother","eta",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter itemAdapter =new WordAdapter(getActivity(),words,R.color.category_family);
        ListView listView =(ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemAdapter);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word =words.get(position);
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.
                    mediaPlayer=MediaPlayer.create(getActivity(),word.getmAudioResourceID());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;

    }

    private void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            //Abandon audio focus
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
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
