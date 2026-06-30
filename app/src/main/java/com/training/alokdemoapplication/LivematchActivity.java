package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.training.alokdemoapplication.model.Match;
import com.training.alokdemoapplication.repository.MatchRepository;
import com.training.alokdemoapplication.ui.adapter.LiveMatchAdapter;
import com.training.alokdemoapplication.util.NetworkUtil;
import com.training.alokdemoapplication.util.Resource;
import com.training.alokdemoapplication.viewmodel.LiveMatchViewModel;

import java.util.List;

/**
 * Shows live cricket scores. MVVM: this Activity only renders state exposed
 * by {@link LiveMatchViewModel}; all networking/caching lives in the
 * repository layer. Auto-refreshes every ~12 seconds while visible.
 */
public class LivematchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private View loadingText, offlineBanner, skeletonContainer;
    private LiveMatchAdapter adapter;
    private LiveMatchViewModel viewModel;
    private MatchRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livematch);

        repository = MatchRepository.getInstance(this);
        viewModel = new ViewModelProvider(this).get(LiveMatchViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        loadingText = findViewById(R.id.loadingText);
        offlineBanner = findViewById(R.id.offlineBanner);
        skeletonContainer = findViewById(R.id.skeletonContainer);

        adapter = new LiveMatchAdapter(new LiveMatchAdapter.Listener() {
            @Override
            public void onMatchClicked(Match match) {
                Intent i = new Intent(LivematchActivity.this, MatchDetailsActivity.class);
                i.putExtra(MatchDetailsActivity.EXTRA_MATCH_ID, match.getMatchId());
                i.putExtra(MatchDetailsActivity.EXTRA_TITLE, match.getTitle());
                i.putExtra(MatchDetailsActivity.EXTRA_TEAM1, match.getTeam1().getTeamName());
                i.putExtra(MatchDetailsActivity.EXTRA_TEAM2, match.getTeam2().getTeamName());
                i.putExtra(MatchDetailsActivity.EXTRA_SCORE1, match.getScore().getTeam1Score());
                i.putExtra(MatchDetailsActivity.EXTRA_SCORE2, match.getScore().getTeam2Score());
                i.putExtra(MatchDetailsActivity.EXTRA_STATUS, match.getStatus());
                i.putExtra(MatchDetailsActivity.EXTRA_VENUE, match.getVenueInfo().getDisplayVenue());
                startActivity(i);
            }

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

        viewModel.getLiveMatches().observe(this, this::render);
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
            loadingText.post(() -> ((android.widget.TextView) loadingText).setText(
                    resource.message != null ? resource.message : "🏏 No live matches right now.\nPlease check back later!"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.startAutoRefresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.stopAutoRefresh();
    }
}
