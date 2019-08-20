package com.xllnc.booksearch.api;

import com.xllnc.booksearch.entity.Books;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookService{

    private static final String BASE_URL = "https://www.googleapis.com/";
    private BookApiService mApiService;
    private BookCallback mListener;

    public BookService(BookCallback listener){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mApiService = retrofit.create(BookApiService.class);

        mListener = listener;
    }



    public void getBooks(String query){
        Call<Books> call = mApiService.getBooks(query);
        call.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                mListener.notifyDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Books> call, Throwable t) {

                mListener.notifyErrorReceived(t);
            }
        });
    }

    public interface BookCallback{
        void notifyDataReceived(Books books);
        void notifyErrorReceived(Throwable error);
    }
}
