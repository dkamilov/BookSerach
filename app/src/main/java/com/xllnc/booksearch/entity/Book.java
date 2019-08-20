package com.xllnc.booksearch.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Book {

    public String getTitle(){
        return mVolumeInfo.mTitle;
    }

    public String getAuthors(){
        if(mVolumeInfo.mAuthors != null && !mVolumeInfo.mAuthors.isEmpty()) {
            return TextUtils.join(", ", mVolumeInfo.mAuthors);
        }
        return "Author not specified";
    }

    public String getSmallThumbnail(){
        return mVolumeInfo.mImageLinks.mSmallThumbnail;
    }


    @SerializedName("volumeInfo")
    private VolumeInfo mVolumeInfo;

        private class VolumeInfo{

            @SerializedName("title")
            private String mTitle;

            @SerializedName("authors")
            private List<String> mAuthors;

            @SerializedName("imageLinks")
            private ImageLinks mImageLinks;

                private class ImageLinks{
                    @SerializedName("smallThumbnail")
                    private String mSmallThumbnail;
                }
        }
}
