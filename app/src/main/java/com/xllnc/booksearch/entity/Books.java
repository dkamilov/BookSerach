package com.xllnc.booksearch.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Books {

    @SerializedName("items")
    @Expose
    private List<Book> items;

    public List<Book> getItems() {
        return items;
    }
}
