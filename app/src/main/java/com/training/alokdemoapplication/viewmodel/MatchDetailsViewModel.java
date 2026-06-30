package com.training.alokdemoapplication.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.training.alokdemoapplication.model.CommentaryItem;
import com.training.alokdemoapplication.repository.MatchRepository;
import com.training.alokdemoapplication.util.Resource;

import java.util.List;

public class MatchDetailsViewModel extends AndroidViewModel {

    private static final long AUTO_REFRESH_INTERVAL_MS = 15_000L;

    private final MatchRepository repository;
    private final MutableLiveData<Resource<List<CommentaryItem>>> commentary = new MutableLiveData<>();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long currentMatchId = -1;
    private boolean autoRefreshing = false;

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentMatchId != -1) loadCommentary(currentMatchId);
            handler.postDelayed(this, AUTO_REFRESH_INTERVAL_MS);
        }
    };

    public MatchDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = MatchRepository.getInstance(application);
    }

    public MutableLiveData<Resource<List<CommentaryItem>>> getCommentary() {
        return commentary;
    }

    public void loadCommentary(long matchId) {
        currentMatchId = matchId;
        commentary.setValue(Resource.loading(commentary.getValue() != null ? commentary.getValue().data : null));
        repository.getCommentary(matchId, resource -> handler.post(() -> commentary.setValue(resource)));
    }

    public void startAutoRefresh() {
        if (autoRefreshing) return;
        autoRefreshing = true;
        handler.postDelayed(refreshRunnable, AUTO_REFRESH_INTERVAL_MS);
    }

    public void stopAutoRefresh() {
        autoRefreshing = false;
        handler.removeCallbacks(refreshRunnable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        stopAutoRefresh();
    }
}
