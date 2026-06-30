package com.training.alokdemoapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.training.alokdemoapplication.data.local.entity.FavoriteMatchEntity;
import com.training.alokdemoapplication.data.local.entity.FavoriteTeamEntity;
import com.training.alokdemoapplication.repository.MatchRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView teamsRv, matchesRv;
    private TextView emptyText;
    private SimpleListAdapter<FavoriteTeamEntity> teamsAdapter;
    private SimpleListAdapter<FavoriteMatchEntity> matchesAdapter;
    private MatchRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        repository = MatchRepository.getInstance(this);

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        teamsRv = findViewById(R.id.favoriteTeamsRecyclerView);
        matchesRv = findViewById(R.id.favoriteMatchesRecyclerView);
        emptyText = findViewById(R.id.emptyText);

        teamsAdapter = new SimpleListAdapter<>(e -> "🏏 " + e.teamName,
                team -> repository.toggleFavoriteTeam(team.teamName, false));
        matchesAdapter = new SimpleListAdapter<>(e -> e.title,
                match -> repository.toggleFavoriteMatch(match.matchId, match.title, false));

        teamsRv.setLayoutManager(new LinearLayoutManager(this));
        teamsRv.setAdapter(teamsAdapter);
        matchesRv.setLayoutManager(new LinearLayoutManager(this));
        matchesRv.setAdapter(matchesAdapter);

        repository.observeFavoriteTeams().observe(this, teams -> {
            teamsAdapter.submit(teams);
            updateEmptyState();
        });
        repository.observeFavoriteMatches().observe(this, matches -> {
            matchesAdapter.submit(matches);
            updateEmptyState();
        });
    }

    private void updateEmptyState() {
        boolean empty = teamsAdapter.getItemCount() == 0 && matchesAdapter.getItemCount() == 0;
        emptyText.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    /** Minimal generic adapter so we don't need a bespoke layout for two simple star-able lists. */
    static class SimpleListAdapter<T> extends RecyclerView.Adapter<SimpleListAdapter.VH> {
        interface Labeler<T> { String label(T item); }
        interface RemoveAction<T> { void remove(T item); }

        private final List<T> items = new ArrayList<>();
        private final Labeler<T> labeler;
        private final RemoveAction<T> removeAction;

        SimpleListAdapter(Labeler<T> labeler, RemoveAction<T> removeAction) {
            this.labeler = labeler;
            this.removeAction = removeAction;
        }

        void submit(List<T> newItems) {
            items.clear();
            if (newItems != null) items.addAll(newItems);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            T item = items.get(position);
            holder.text1.setText(labeler.label(item));
            holder.text2.setText("Tap to remove from favorites");
            holder.itemView.setOnClickListener(v -> removeAction.remove(item));
        }

        @Override
        public int getItemCount() { return items.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView text1, text2;
            VH(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
