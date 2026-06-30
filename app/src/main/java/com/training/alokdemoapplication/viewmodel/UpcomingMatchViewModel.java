package com.training.alokdemoapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.training.alokdemoapplication.model.Match;
import com.training.alokdemoapplication.repository.MatchRepository;
import com.training.alokdemoapplication.util.Resource;

import java.util.List;

public class UpcomingMatchViewModel extends AndroidViewModel {

    private final MatchRepository repository;
    private final MediatorLiveData<Resource<List<Match>>> upcomingMatches = new MediatorLiveData<>();

    public UpcomingMatchViewModel(@NonNull Application application) {
        super(application);
        repository = MatchRepository.getInstance(application);
        refresh();
    }

    public LiveData<Resource<List<Match>>> getUpcomingMatches() {
        return upcomingMatches;
    }

    public void refresh() {
        LiveData<Resource<List<Match>>> source = repository.getUpcomingMatches();
        upcomingMatches.addSource(source, value -> {
            upcomingMatches.setValue(value);
            if (value.status != Resource.Status.LOADING) {
                upcomingMatches.removeSource(source);
            }
        });
    }
}
