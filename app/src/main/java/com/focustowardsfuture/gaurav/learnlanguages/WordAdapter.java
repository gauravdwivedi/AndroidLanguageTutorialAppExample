package com.focustowardsfuture.gaurav.learnlanguages;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gaurav Dwivedi on 05-05-2019.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    private static final String TAG =WordAdapter.class.getSimpleName();
    //Resource ID for the background color
    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words,int colorResouceId){
        super(context,0,words);
        mColorResourceId=colorResouceId;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
       View listItemView =convertView;
       if(listItemView==null){
           listItemView= LayoutInflater.from(getContext()).inflate(
                   R.layout.list_item,parent,false
           );
       }
       Word wordlist =getItem(position);


        TextView defaultText =(TextView) listItemView.findViewById(R.id.defaulttranslation);
       defaultText.setText(wordlist.getmDefaultTranslation());

       TextView miWokTranslation =(TextView) listItemView.findViewById(R.id.miWokTranslation);
       miWokTranslation.setText(wordlist.getmMiwokTranslation());

        ImageView iconView =(ImageView) listItemView.findViewById(R.id.image);
        if(wordlist.hasImage()) {
            iconView.setImageResource(wordlist.getImageResourceID());
            iconView.setVisibility(View.VISIBLE);
        }
        else{
              iconView.setVisibility(View.GONE);
        }


        //set the theme color for he list_item
       View textContainer = listItemView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(),mColorResourceId);

        textContainer.setBackgroundColor(color);



       return listItemView;

    }
}
