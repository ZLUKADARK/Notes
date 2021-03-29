package com.example.notes.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.EditActivity;
import com.example.notes.R;
import com.example.notes.db.MyConstans;
import com.example.notes.db.MyDbManager;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<ListItem> mainArray;

    public MainAdapter(Context context) {
        this.context = context;
        mainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent,false);
        return new MyViewHolder(view, context, mainArray);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mainArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ltitle;
        private Context context;
        private List<ListItem> mainArray;

        public MyViewHolder(@NonNull View itemView, Context context, List<ListItem> mainArray) {
            super(itemView);
            this.context = context;
            this.mainArray = mainArray;
            ltitle = itemView.findViewById(R.id.ltitle);
            itemView.setOnClickListener(this);
        }
        public void setData(String title) {
            ltitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(MyConstans.ITEM_INTENT, mainArray.get(getAdapterPosition()));
            intent.putExtra(MyConstans.EDIT_STATE, false);
            context.startActivity(intent);
        }
    }

    public void updateAdapter(List<ListItem> newList) {
        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int position, MyDbManager dbManager){
        dbManager.deleteitem(mainArray.get(position).getId());
        mainArray.remove(position);
        notifyItemRangeChanged(0, mainArray.size());
        notifyItemRemoved(position);

    }
}
