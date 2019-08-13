package com.xllnc.booksearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> mItems;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BooksAsyncTask().execute();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

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
            mAuthor.setText(book.getAuthor());
            Picasso.get().load(book.getSmallThumbnailUrl()).into(mSmallThumbnail);
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder>{
        private List<Book> mItems;

        public BookAdapter(List<Book> items){
            mItems = items;
        }

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item_view, parent, false);
            return new BookHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            Book book = mItems.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {
            return this.mItems.size();
        }
    }

    private class BooksAsyncTask extends AsyncTask<Void, Void, List<Book>> {


        @Override
        protected List<Book> doInBackground(Void... voids) {
            return new FetchBooks().buildJsonString();
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            mItems = books;
            mRecyclerView.setAdapter(new BookAdapter(mItems));
        }
    }
}
