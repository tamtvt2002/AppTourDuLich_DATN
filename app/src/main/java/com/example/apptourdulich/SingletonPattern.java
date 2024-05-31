package com.example.apptourdulich;

import com.example.apptourdulich.Domains.Photo;

import java.util.ArrayList;
import java.util.List;

public class SingletonPattern {
    private List<Photo> mPhotos;
    private SingletonPattern() {
        mPhotos = new ArrayList<>();
        for(int i = 1; i < imgid.length; i++){

            Photo photo = new Photo ();
            photo.setResourceID(R.drawable.id);

            mPhotos.add(new Photo(imgid[i]));
        }
    }
    public List<Photo> getmPhotos(){
        return mPhotos;
    }
    private static SingletonPattern mSingletonPattern;
    public static SingletonPattern get() {
        if (mSingletonPattern == null){
            mSingletonPattern = new SingletonPattern();
        }
        return mSingletonPattern;
    }
    private Integer[] imgid =
            {
                    R.drawable.im1,R.drawable.im2,R.drawable.im3,
                    R.drawable.im4
            };

}
