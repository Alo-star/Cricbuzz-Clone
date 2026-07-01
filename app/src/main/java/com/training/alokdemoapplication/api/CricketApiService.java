package com.training.alokdemoapplication.api;

import com.training.alokdemoapplication.model.CommentaryResponse;
import com.training.alokdemoapplication.model.LiveMatchesResponse;
import com.training.alokdemoapplication.model.ScheduleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/** Retrofit endpoint definitions for the free-cricbuzz-cricket-api (RapidAPI). */
public interface CricketApiService {

    @GET("cricket-livescores")
    Call<LiveMatchesResponse> getLiveMatches();

    @GET("cricket-schedule")
    Call<ScheduleResponse> getSchedule();

    @GET("mcenter/v1/{matchId}/comm")
    Call<CommentaryResponse> getCommentary(@Path("matchId") long matchId);
}
