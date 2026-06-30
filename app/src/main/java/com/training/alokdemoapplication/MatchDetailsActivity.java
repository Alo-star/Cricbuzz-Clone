package com.training.alokdemoapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.training.alokdemoapplication.repository.MatchRepository;
import com.training.alokdemoapplication.ui.adapter.CommentaryAdapter;
import com.training.alokdemoapplication.util.Resource;
import com.training.alokdemoapplication.viewmodel.MatchDetailsViewModel;

/** Scorecard + ball-by-ball commentary for a single match, auto-refreshing every ~15s. */
public class MatchDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MATCH_ID = "extra_match_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_TEAM1 = "extra_team1";
    public static final String EXTRA_TEAM2 = "extra_team2";
    public static final String EXTRA_SCORE1 = "extra_score1";
    public static final String EXTRA_SCORE2 = "extra_score2";
    public static final String EXTRA_STATUS = "extra_status";
    public static final String EXTRA_VENUE = "extra_venue";

    private MatchDetailsViewModel viewModel;
    private MatchRepository repository;
    private CommentaryAdapter adapter;
    private RecyclerView commentaryRecyclerView;
    private TextView commentaryStatusText;
    private ImageView favoriteIcon;
    private String matchId;
    private String matchTitle;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        repository = MatchRepository.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MatchDetailsViewModel.class);

        long matchIdLong = getIntent().getLongExtra(EXTRA_MATCH_ID, -1);
        matchId = String.valueOf(matchIdLong);
        matchTitle = getIntent().getStringExtra(EXTRA_TITLE);

        ((TextView) findViewById(R.id.matchTitle)).setText(matchTitle);
        ((TextView) findViewById(R.id.team1Name)).setText(getIntent().getStringExtra(EXTRA_TEAM1));
        ((TextView) findViewById(R.id.team2Name)).setText(getIntent().getStringExtra(EXTRA_TEAM2));
        ((TextView) findViewById(R.id.team1Score)).setText(getIntent().getStringExtra(EXTRA_SCORE1));
        ((TextView) findViewById(R.id.team2Score)).setText(getIntent().getStringExtra(EXTRA_SCORE2));
        ((TextView) findViewById(R.id.matchStatus)).setText(getIntent().getStringExtra(EXTRA_STATUS));
        ((TextView) findViewById(R.id.matchVenue)).setText("📍 " + getIntent().getStringExtra(EXTRA_VENUE));

        favoriteIcon = findViewById(R.id.favoriteIcon);
        isFavorite = repository.isFavoriteMatch(matchId);
        updateFavoriteIcon();
        favoriteIcon.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            repository.toggleFavoriteMatch(matchId, matchTitle, isFavorite);
            updateFavoriteIcon();
        });

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        commentaryRecyclerView = findViewById(R.id.commentaryRecyclerView);
        commentaryStatusText = findViewById(R.id.commentaryStatusText);
        adapter = new CommentaryAdapter();
        commentaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentaryRecyclerView.setAdapter(adapter);

        viewModel.getCommentary().observe(this, resource -> {
            if (resource == null) return;
            if (resource.status == Resource.Status.LOADING && resource.data == null) {
                commentaryStatusText.setVisibility(View.VISIBLE);
                commentaryStatusText.setText("Loading commentary...");
                commentaryRecyclerView.setVisibility(View.GONE);
                return;
            }
            if (resource.data != null && !resource.data.isEmpty()) {
                commentaryStatusText.setVisibility(View.GONE);
                commentaryRecyclerView.setVisibility(View.VISIBLE);
                adapter.submitList(resource.data);
            } else {
                commentaryRecyclerView.setVisibility(View.GONE);
                commentaryStatusText.setVisibility(View.VISIBLE);
                commentaryStatusText.setText(resource.message != null
                        ? resource.message
                        : "No commentary available yet.");
            }
        });

        if (matchIdLong != -1) viewModel.loadCommentary(matchIdLong);
    }

    private void updateFavoriteIcon() {
        favoriteIcon.setImageResource(isFavorite
                ? android.R.drawable.btn_star_big_on
                : android.R.drawable.btn_star_big_off);
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
