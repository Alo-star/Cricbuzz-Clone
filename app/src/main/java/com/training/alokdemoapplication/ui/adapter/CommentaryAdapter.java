package com.training.alokdemoapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.training.alokdemoapplication.R;
import com.training.alokdemoapplication.model.CommentaryItem;

import java.util.ArrayList;
import java.util.List;

public class CommentaryAdapter extends RecyclerView.Adapter<CommentaryAdapter.VH> {

    private final List<CommentaryItem> items = new ArrayList<>();

    public void submitList(List<CommentaryItem> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commentary, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CommentaryItem item = items.get(position);
        holder.over.setText(item.getOverNumber());
        holder.text.setText(item.getCommentaryText());
        holder.itemView.setAlpha(0f);
        holder.itemView.animate().alpha(1f).setDuration(250).start();
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView over, text;
        VH(@NonNull View itemView) {
            super(itemView);
            over = itemView.findViewById(R.id.overNumber);
            text = itemView.findViewById(R.id.commentaryText);
        }
    }
}
