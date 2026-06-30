package com.training.alokdemoapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.training.alokdemoapplication.R;
import com.training.alokdemoapplication.model.Match;

import java.util.ArrayList;
import java.util.List;

public class LiveMatchAdapter extends RecyclerView.Adapter<LiveMatchAdapter.VH> {

    public interface Listener {
        void onMatchClicked(Match match);
        void onFavoriteToggled(Match match, boolean nowFavorite);
        boolean isFavorite(Match match);
    }

    private final List<Match> items = new ArrayList<>();
    private final Listener listener;

    public LiveMatchAdapter(Listener listener) {
        this.listener = listener;
    }

    public void submitList(List<Match> newItems) {
        List<Match> old = new ArrayList<>(items);
        items.clear();
        if (newItems != null) items.addAll(newItems);
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() { return old.size(); }
            @Override public int getNewListSize() { return items.size(); }
            @Override public boolean areItemsTheSame(int oldPos, int newPos) {
                return old.get(oldPos).getMatchId() == items.get(newPos).getMatchId();
            }
            @Override public boolean areContentsTheSame(int oldPos, int newPos) {
                Match a = old.get(oldPos), b = items.get(newPos);
                return a.getStatus().equals(b.getStatus())
                        && a.getScore().getTeam1Score().equals(b.getScore().getTeam1Score())
                        && a.getScore().getTeam2Score().equals(b.getScore().getTeam2Score());
            }
        }).dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_match, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Match m = items.get(position);
        holder.title.setText(m.getTitle());
        holder.team1Name.setText(m.getTeam1().getTeamName());
        holder.team1Score.setText(m.getScore().getTeam1Score());
        holder.team2Name.setText(m.getTeam2().getTeamName());
        holder.team2Score.setText(m.getScore().getTeam2Score());
        holder.status.setText(m.getStatus());

        boolean fav = listener != null && listener.isFavorite(m);
        holder.favoriteIcon.setImageResource(fav
                ? android.R.drawable.btn_star_big_on
                : android.R.drawable.btn_star_big_off);

        holder.itemView.setAlpha(0f);
        holder.itemView.animate().alpha(1f).setDuration(300).start();

        holder.itemView.setOnClickListener(v -> { if (listener != null) listener.onMatchClicked(m); });
        holder.favoriteIcon.setOnClickListener(v -> {
            if (listener == null) return;
            boolean newState = !listener.isFavorite(m);
            listener.onFavoriteToggled(m, newState);
            holder.favoriteIcon.setImageResource(newState
                    ? android.R.drawable.btn_star_big_on
                    : android.R.drawable.btn_star_big_off);
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, team1Name, team1Score, team2Name, team2Score, status;
        ImageView favoriteIcon;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.matchTitle);
            team1Name = itemView.findViewById(R.id.team1Name);
            team1Score = itemView.findViewById(R.id.team1Score);
            team2Name = itemView.findViewById(R.id.team2Name);
            team2Score = itemView.findViewById(R.id.team2Score);
            status = itemView.findViewById(R.id.matchStatus);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}
