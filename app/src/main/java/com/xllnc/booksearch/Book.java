package com.xllnc.booksearch;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String mTitle;
    private String mSmallThumbnailUrl;
    private List<String> mAuthors;

    public Book(){
        mAuthors = new ArrayList<>();
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setSmallThumbnailUrl(String smallThumbnailUrl) {
        mSmallThumbnailUrl = smallThumbnailUrl;
    }

    public String getSmallThumbnailUrl() {
        return mSmallThumbnailUrl;
    }

    public void addAuthor(String author){
        mAuthors.add(author);
    }

    public String getAuthor(){
        return TextUtils.join(", ", mAuthors);
    }
}
