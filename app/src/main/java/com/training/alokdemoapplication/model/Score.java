package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("team1Score")
    private String team1Score;

    @SerializedName("team2Score")
    private String team2Score;

    public String getTeam1Score() { return team1Score == null ? "-" : team1Score; }
    public String getTeam2Score() { return team2Score == null ? "-" : team2Score; }

    public void setTeam1Score(String v) { this.team1Score = v; }
    public void setTeam2Score(String v) { this.team2Score = v; }
}
