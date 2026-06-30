package com.training.alokdemoapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.training.alokdemoapplication.model.Match;
import com.training.alokdemoapplication.repository.MatchRepository;
import com.training.alokdemoapplication.ui.adapter.UpcomingMatchAdapter;
import com.training.alokdemoapplication.util.NetworkUtil;
import com.training.alokdemoapplication.util.Resource;
import com.training.alokdemoapplication.viewmodel.UpcomingMatchViewModel;

import java.util.List;

public class UpcomingMatchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View loadingText, offlineBanner, skeletonContainer;
    private UpcomingMatchAdapter adapter;
    private UpcomingMatchViewModel viewModel;
    private MatchRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomingmatch);

        repository = MatchRepository.getInstance(this);
        viewModel = new ViewModelProvider(this).get(UpcomingMatchViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        loadingText = findViewById(R.id.loadingText);
        offlineBanner = findViewById(R.id.offlineBanner);
        skeletonContainer = findViewById(R.id.skeletonContainer);

        adapter = new UpcomingMatchAdapter(new UpcomingMatchAdapter.Listener() {
            @Override
            public void onFavoriteToggled(Match match, boolean nowFavorite) {
                repository.toggleFavoriteMatch(String.valueOf(match.getMatchId()), match.getTitle(), nowFavorite);
            }

            @Override
            public boolean isFavorite(Match match) {
                return repository.isFavoriteMatch(String.valueOf(match.getMatchId()));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
        findViewById(R.id.refreshBtn).setOnClickListener(v -> viewModel.refresh());
        swipeRefresh.setOnRefreshListener(() -> viewModel.refresh());

        viewModel.getUpcomingMatches().observe(this, this::render);
    }

    private void render(Resource<List<Match>> resource) {
        swipeRefresh.setRefreshing(resource.status == Resource.Status.LOADING);
        offlineBanner.setVisibility(!NetworkUtil.isOnline(this) && resource.data != null ? View.VISIBLE : View.GONE);

        if (resource.status == Resource.Status.LOADING && resource.data == null) {
            recyclerView.setVisibility(View.GONE);
            loadingText.setVisibility(View.GONE);
            skeletonContainer.setVisibility(View.VISIBLE);
            return;
        }
        skeletonContainer.setVisibility(View.GONE);

        if (resource.data != null && !resource.data.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.GONE);
            adapter.submitList(resource.data);
        } else {
            recyclerView.setVisibility(View.GONE);
            loadingText.setVisibility(View.VISIBLE);
            ((android.widget.TextView) loadingText).setText(
                    resource.message != null ? resource.message : "No upcoming matches found.");
        }
    }
}
