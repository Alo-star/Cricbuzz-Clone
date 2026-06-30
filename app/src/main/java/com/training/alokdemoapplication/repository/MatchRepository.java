package com.training.alokdemoapplication.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.training.alokdemoapplication.api.ApiClient;
import com.training.alokdemoapplication.data.local.AppDatabase;
import com.training.alokdemoapplication.data.local.entity.FavoriteMatchEntity;
import com.training.alokdemoapplication.data.local.entity.FavoriteTeamEntity;
import com.training.alokdemoapplication.data.local.entity.MatchEntity;
import com.training.alokdemoapplication.model.CommentaryItem;
import com.training.alokdemoapplication.model.CommentaryResponse;
import com.training.alokdemoapplication.model.LiveMatchesResponse;
import com.training.alokdemoapplication.model.Match;
import com.training.alokdemoapplication.model.Score;
import com.training.alokdemoapplication.model.ScheduleEntry;
import com.training.alokdemoapplication.model.ScheduleResponse;
import com.training.alokdemoapplication.model.SeriesSchedule;
import com.training.alokdemoapplication.model.Team;
import com.training.alokdemoapplication.model.Venue;
import com.training.alokdemoapplication.util.NetworkUtil;
import com.training.alokdemoapplication.util.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Single source of truth for match data. Mediates between the Retrofit API
 * layer and the Room cache so ViewModels never talk to either directly.
 */
public class MatchRepository {

    private static final String TYPE_LIVE = "LIVE";
    private static final String TYPE_UPCOMING = "UPCOMING";

    private static volatile MatchRepository INSTANCE;

    private final AppDatabase db;
    private final Context appContext;
    private final ExecutorService ioExecutor = Executors.newFixedThreadPool(3);

    private MatchRepository(Context context) {
        this.appContext = context.getApplicationContext();
        this.db = AppDatabase.getInstance(appContext);
    }

    public static MatchRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MatchRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MatchRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    // ---------------------------------------------------------------- LIVE
    public LiveData<Resource<List<Match>>> getLiveMatches() {
        MutableLiveData<Resource<List<Match>>> result = new MutableLiveData<>();
        List<Match> cached = readCache(TYPE_LIVE);
        result.setValue(Resource.loading(cached.isEmpty() ? null : cached));

        if (!NetworkUtil.isOnline(appContext)) {
            result.setValue(cached.isEmpty()
                    ? Resource.error("No internet connection.", null)
                    : Resource.error("No internet connection. Showing cached data.", cached));
            return result;
        }

        ApiClient.getService().getLiveMatches().enqueue(new Callback<LiveMatchesResponse>() {
            @Override
            public void onResponse(@NonNull Call<LiveMatchesResponse> call, @NonNull Response<LiveMatchesResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMatches() != null) {
                    List<Match> matches = response.body().getMatches();
                    if (matches.isEmpty()) {
                        result.postValue(Resource.error("No live matches right now. Please check back later!", null));
                    } else {
                        result.postValue(Resource.success(matches));
                        cacheMatches(matches, TYPE_LIVE);
                    }
                } else {
                    List<Match> fallback = readCache(TYPE_LIVE);
                    result.postValue(Resource.error("Server error (" + response.code() + ").", fallback.isEmpty() ? null : fallback));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LiveMatchesResponse> call, @NonNull Throwable t) {
                List<Match> fallback = readCache(TYPE_LIVE);
                result.postValue(Resource.error("Couldn't reach the server: " + t.getMessage(), fallback.isEmpty() ? null : fallback));
            }
        });

        return result;
    }

    // ----------------------------------------------------------- UPCOMING
    public LiveData<Resource<List<Match>>> getUpcomingMatches() {
        MutableLiveData<Resource<List<Match>>> result = new MutableLiveData<>();
        List<Match> cached = readCache(TYPE_UPCOMING);
        result.setValue(Resource.loading(cached.isEmpty() ? null : cached));

        if (!NetworkUtil.isOnline(appContext)) {
            result.setValue(cached.isEmpty()
                    ? Resource.error("No internet connection.", null)
                    : Resource.error("No internet connection. Showing cached data.", cached));
            return result;
        }

        ApiClient.getService().getSchedule().enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponseBody() != null) {
                    List<Match> matches = flattenSchedule(response.body());
                    if (matches.isEmpty()) {
                        result.postValue(Resource.error("No upcoming matches found.", null));
                    } else {
                        result.postValue(Resource.success(matches));
                        cacheMatches(matches, TYPE_UPCOMING);
                    }
                } else {
                    List<Match> fallback = readCache(TYPE_UPCOMING);
                    result.postValue(Resource.error("Server error (" + response.code() + ").", fallback.isEmpty() ? null : fallback));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ScheduleResponse> call, @NonNull Throwable t) {
                List<Match> fallback = readCache(TYPE_UPCOMING);
                result.postValue(Resource.error("Couldn't reach the server: " + t.getMessage(), fallback.isEmpty() ? null : fallback));
            }
        });

        return result;
    }

    private List<Match> flattenSchedule(ScheduleResponse body) {
        List<Match> out = new ArrayList<>();
        if (body.getResponseBody() == null || body.getResponseBody().getSchedules() == null) return out;
        for (ScheduleEntry entry : body.getResponseBody().getSchedules()) {
            if (entry.getScheduleAdWrapper() == null || entry.getScheduleAdWrapper().getMatchScheduleList() == null) continue;
            for (SeriesSchedule series : entry.getScheduleAdWrapper().getMatchScheduleList()) {
                if (series.getMatchInfo() == null) continue;
                for (Match m : series.getMatchInfo()) {
                    out.add(m);
                }
            }
        }
        return out;
    }

    // ---------------------------------------------------------- COMMENTARY
    public interface CommentaryCallback {
        void onResult(Resource<List<CommentaryItem>> resource);
    }

    public void getCommentary(long matchId, CommentaryCallback callback) {
        if (!NetworkUtil.isOnline(appContext)) {
            callback.onResult(Resource.error("No internet connection.", null));
            return;
        }
        ApiClient.getService().getCommentary(matchId).enqueue(new Callback<CommentaryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommentaryResponse> call, @NonNull Response<CommentaryResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getCommentaryList() != null
                        && !response.body().getCommentaryList().isEmpty()) {
                    callback.onResult(Resource.success(response.body().getCommentaryList()));
                } else {
                    callback.onResult(Resource.error("Commentary not available for this match yet.", null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentaryResponse> call, @NonNull Throwable t) {
                callback.onResult(Resource.error("Couldn't load commentary: " + t.getMessage(), null));
            }
        });
    }

    // ------------------------------------------------------------- CACHE
    private void cacheMatches(List<Match> matches, String type) {
        ioExecutor.execute(() -> {
            List<MatchEntity> entities = new ArrayList<>();
            long now = System.currentTimeMillis();
            for (Match m : matches) {
                MatchEntity e = new MatchEntity();
                e.matchId = type + "_" + m.getMatchId();
                e.title = m.getTitle();
                e.team1Name = m.getTeam1().getTeamName();
                e.team1Score = m.getScore().getTeam1Score();
                e.team2Name = m.getTeam2().getTeamName();
                e.team2Score = m.getScore().getTeam2Score();
                e.status = m.getStatus();
                e.startDate = m.getStartDate();
                e.venue = m.getVenueInfo().getDisplayVenue();
                e.type = type;
                e.cachedAt = now;
                entities.add(e);
            }
            db.matchDao().clearType(type);
            db.matchDao().insertAll(entities);
        });
    }

    private List<Match> readCache(String type) {
        List<Match> out = new ArrayList<>();
        try {
            List<MatchEntity> entities = db.matchDao().getByType(type);
            for (MatchEntity e : entities) {
                Match m = new Match();
                m.setSeriesName("");
                m.setMatchDesc(e.title);
                m.setStatus(e.status);
                m.setStartDate(e.startDate);

                Team t1 = new Team();
                t1.setTeamName(e.team1Name);
                Team t2 = new Team();
                t2.setTeamName(e.team2Name);
                m.setTeam1(t1);
                m.setTeam2(t2);

                Score s = new Score();
                s.setTeam1Score(e.team1Score);
                s.setTeam2Score(e.team2Score);
                m.setScore(s);

                Venue v = new Venue();
                v.setGround(e.venue);
                m.setVenueInfo(v);

                out.add(m);
            }
        } catch (Exception ignored) {
            // DB not ready / first run — treat as empty cache.
        }
        return out;
    }

    // ----------------------------------------------------------- FAVORITES
    public LiveData<List<FavoriteTeamEntity>> observeFavoriteTeams() {
        return db.favoriteTeamDao().observeAll();
    }

    public LiveData<List<FavoriteMatchEntity>> observeFavoriteMatches() {
        return db.favoriteMatchDao().observeAll();
    }

    public void toggleFavoriteTeam(String teamName, boolean isFavorite) {
        ioExecutor.execute(() -> {
            if (isFavorite) {
                db.favoriteTeamDao().insert(new FavoriteTeamEntity(teamName, System.currentTimeMillis()));
            } else {
                FavoriteTeamEntity e = new FavoriteTeamEntity(teamName, 0);
                db.favoriteTeamDao().delete(e);
            }
        });
    }

    public void toggleFavoriteMatch(String matchId, String title, boolean isFavorite) {
        ioExecutor.execute(() -> {
            if (isFavorite) {
                db.favoriteMatchDao().insert(new FavoriteMatchEntity(matchId, title, System.currentTimeMillis()));
            } else {
                FavoriteMatchEntity e = new FavoriteMatchEntity(matchId, title, 0);
                db.favoriteMatchDao().delete(e);
            }
        });
    }

    public boolean isFavoriteTeam(String teamName) {
        return db.favoriteTeamDao().isFavorite(teamName);
    }

    public boolean isFavoriteMatch(String matchId) {
        return db.favoriteMatchDao().isFavorite(matchId);
    }
}
