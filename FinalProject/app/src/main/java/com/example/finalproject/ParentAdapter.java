package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {
    private List<ParentModelClass> parentModelClassList;
    private List<ParentModelClass> originalParentModelClassList; // Store the original list for filtering
    private Context context;


    public ParentAdapter(List<ParentModelClass> parentModelClassList, Context context) {
        this.parentModelClassList = parentModelClassList;
        this.originalParentModelClassList = new ArrayList<>(parentModelClassList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParentModelClass parentModel = parentModelClassList.get(position);
        holder.tv_parent_title.setText(parentModel.getTitle());

        ChildAdapter childAdapter = new ChildAdapter(parentModel.getChildModelClassList(), context);
        holder.rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_child.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return parentModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_child;
        TextView tv_parent_title;
        TextView tv_see_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_child = itemView.findViewById(R.id.rv_child);
            tv_parent_title = itemView.findViewById(R.id.textView_book);
            tv_see_more = itemView.findViewById(R.id.textView_book1);
        }
    }

}



