package com.focustowardsfuture.gaurav.learnlanguages;

/**
 * Created by Gaurav Dwivedi on 05-05-2019.
 */
public class Word {

private String mDefaultTranslation;

private String mMiwokTranslation;
private int mImageResourceId=NO_IMAGE_PROVIDED;
private static final int NO_IMAGE_PROVIDED=-1;

private int mAudioResourceID;



    public Word(String mDefaultTranslation, String mMiwokTranslation,int AudioResourceID) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mAudioResourceID=AudioResourceID;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation,int mImageResourceId,int AudioResourceID){

        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId=mImageResourceId;
        this.mAudioResourceID=AudioResourceID;
    }


    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public void setmDefaultTranslation(String mDefaultTranslation) {
        this.mDefaultTranslation = mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public void setmMiwokTranslation(String mMiwokTranslation) {
        this.mMiwokTranslation = mMiwokTranslation;
    }

    public int getImageResourceID(){
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    public boolean hasImage(){
        return  mImageResourceId!=NO_IMAGE_PROVIDED;
    }
    public int getmAudioResourceID(){
        return mAudioResourceID;
    }
}
