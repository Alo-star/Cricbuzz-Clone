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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingMatchAdapter extends RecyclerView.Adapter<UpcomingMatchAdapter.VH> {

    public interface Listener {
        void onFavoriteToggled(Match match, boolean nowFavorite);
        boolean isFavorite(Match match);
    }

    private final List<Match> items = new ArrayList<>();
    private final Listener listener;

    public UpcomingMatchAdapter(Listener listener) {
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
                return old.get(oldPos).getTitle().equals(items.get(newPos).getTitle());
            }
        }).dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_match, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Match m = items.get(position);
        holder.title.setText(m.getTitle());
        holder.team1.setText(m.getTeam1().getTeamName());
        holder.team2.setText(m.getTeam2().getTeamName());
        holder.venue.setText("📍 " + m.getVenueInfo().getDisplayVenue());

        String time = "";
        try {
            long ts = Long.parseLong(m.getStartDate());
            if (ts > 0) time = new SimpleDateFormat("dd MMM yyyy · hh:mm a", Locale.getDefault()).format(new Date(ts));
        } catch (Exception ignored) {}
        holder.date.setText("🗓 " + (time.isEmpty() ? "Date TBA" : time));

        boolean fav = listener != null && listener.isFavorite(m);
        holder.favoriteIcon.setImageResource(fav
                ? android.R.drawable.btn_star_big_on
                : android.R.drawable.btn_star_big_off);

        holder.itemView.setAlpha(0f);
        holder.itemView.animate().alpha(1f).setDuration(300).start();

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
        TextView title, date, venue, team1, team2;
        ImageView favoriteIcon;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.matchTitle);
            date = itemView.findViewById(R.id.matchDate);
            venue = itemView.findViewById(R.id.matchVenue);
            team1 = itemView.findViewById(R.id.team1Name);
            team2 = itemView.findViewById(R.id.team2Name);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}
