package com.xllnc.booksearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xllnc.booksearch.api.BookService;
import com.xllnc.booksearch.entity.Book;
import com.xllnc.booksearch.entity.Books;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BookService.BookCallback {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private BookAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new BookService(this).getBooks("android");
    }

    @Override
    public void notifyDataReceived(Books books) {
        if(books.getItems() != null) {
            List<Book> items = books.getItems();
            Log.d(TAG, "Success");
            Log.d(TAG, "Size " + items.size());
            for(Book book : items){
                Log.d(TAG, "Title: " + book.getTitle());
                Log.d(TAG, "Authors : " + book.getAuthors());
            }
            setupAdapter(items);
        }
    }

    @Override
    public void notifyErrorReceived(Throwable error) {
        Log.d(TAG, "Error " + error.getMessage());
    }

    private void setupAdapter(List<Book> items){
        mBookAdapter = new BookAdapter();
        mBookAdapter.setItems(items);
        mRecyclerView.setAdapter(mBookAdapter);
    }

    /**
     *
     *
     * ViewHolder
     *
     *
     */
    private class BookHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mAuthor;
        private ImageView mSmallThumbnail;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mAuthor = itemView.findViewById(R.id.author);
            mSmallThumbnail = itemView.findViewById(R.id.image);
        }

        public void bind(Book book){
            mTitle.setText(book.getTitle());
            mAuthor.setText(book.getAuthors());
            Picasso.get().load(book.getSmallThumbnail()).into(mSmallThumbnail);
        }
    }

    /**
     *
     *
     * Adapter
     *
     *
     */
    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {

        private List<Book> items;

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            Book book = items.get(position);
            holder.bind(book);
        }


        @Override
        public int getItemCount() {
            if (items.size() != 0)
                return items.size();
            return 0;
        }

        public void setItems(List<Book> items) {
            this.items = items;
        }
    }
}
