package com.training.alokdemoapplication.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.training.alokdemoapplication.model.Match;
import com.training.alokdemoapplication.repository.MatchRepository;
import com.training.alokdemoapplication.util.Resource;

import java.util.List;

public class LiveMatchViewModel extends AndroidViewModel {

    private static final long AUTO_REFRESH_INTERVAL_MS = 12_000L; // 10-15s window

    private final MatchRepository repository;
    private final MediatorLiveData<Resource<List<Match>>> liveMatches = new MediatorLiveData<>();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean autoRefreshing = false;

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            refresh();
            handler.postDelayed(this, AUTO_REFRESH_INTERVAL_MS);
        }
    };

    public LiveMatchViewModel(@NonNull Application application) {
        super(application);
        repository = MatchRepository.getInstance(application);
        refresh();
    }

    public LiveData<Resource<List<Match>>> getLiveMatches() {
        return liveMatches;
    }

    public void refresh() {
        LiveData<Resource<List<Match>>> source = repository.getLiveMatches();
        liveMatches.addSource(source, value -> {
            liveMatches.setValue(value);
            if (value.status != Resource.Status.LOADING) {
                liveMatches.removeSource(source);
            }
        });
    }

    /** Starts polling the live-scores endpoint every ~12 seconds. */
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
