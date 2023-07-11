package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private Context context;
    private ArrayList<ChildModelClass> bookList;

    public BookAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ChildModelClass> bookList){
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rv_layout,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.nameBook.setText(bookList.get(position).getBook_Title());
        holder.nameAuthor.setText(bookList.get(position).getBook_Author());
        Glide.with(context)
                .load(bookList.get(position).getImage_URL())
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Lưu cache ảnh
                .into(holder.iv_child_image);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_child_image;
        private TextView nameBook,nameAuthor;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_child_image = itemView.findViewById(R.id.iv_child_item);
            nameBook = itemView.findViewById(R.id.nameBook);
            nameAuthor = itemView.findViewById(R.id.nameAuthor);
        }
    }
}
