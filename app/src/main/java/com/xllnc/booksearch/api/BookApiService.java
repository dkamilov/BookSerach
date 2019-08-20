package com.xllnc.booksearch.api;

import com.xllnc.booksearch.entity.Books;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApiService {

    @GET("/books/v1/volumes")
    Call<Books> getBooks(@Query("q") String query);
}
