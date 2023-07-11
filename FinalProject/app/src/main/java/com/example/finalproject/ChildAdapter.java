package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.List;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    List<ChildModelClass> childModelClassList;
    Context context;

    public ChildAdapter(List<ChildModelClass> childModelClassList, Context context) {
        this.childModelClassList = childModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_rv_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder holder, int position) {
        holder.nameBook.setText(childModelClassList.get(position).getBook_Title());
        holder.nameAuthor.setText(childModelClassList.get(position).getBook_Author());
        Glide.with(context)
                .load(childModelClassList.get(position).getImage_URL())
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Lưu cache ảnh
                .into(holder.iv_child_image);
        holder.rlt_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(childModelClassList.get(position).getBook_Id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return childModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlt_book;
        ImageView iv_child_image;
        TextView nameBook,nameAuthor;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            iv_child_image = itemView.findViewById(R.id.iv_child_item);
            nameBook = itemView.findViewById(R.id.nameBook);
            nameAuthor = itemView.findViewById(R.id.nameAuthor);
            rlt_book = itemView.findViewById(R.id.rlt_layoutBook);
        }
    }

    public void onClickItem(String book_Id){
        Intent intent = new Intent(context,BookDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book_Id",book_Id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
