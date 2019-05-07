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
public class PhrasesFragment extends Fragment {
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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.word_list,container,false);


        final ArrayList<Word> words =new ArrayList<>();

        //Word w =new Word("one","lutti");
        words.add(new Word("Where are you going ?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name ?","tinne oyaase'ne",
                R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...","oyaase'ne",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michakses",
                R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeing good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","eenes'aa",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes,I'm coming.","hee'eenem",R.raw.phrase_yes_im_coming));
        words.add(new Word("let's go","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here","anni'nem",R.raw.phrase_come_here));

        WordAdapter itemAdapter =new WordAdapter(getActivity(),words,R.color.category_phrases);
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
        return  rootView;

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
