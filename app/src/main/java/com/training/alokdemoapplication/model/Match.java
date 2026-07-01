package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a single cricket match as returned by both the live-scores
 * and schedule endpoints. Fields are nullable/optional since the two
 * endpoints don't return identical shapes.
 */
public class Match {
    @SerializedName("matchId")
    private long matchId;

    @SerializedName("seriesName")
    private String seriesName;

    @SerializedName("matchDesc")
    private String matchDesc;

    @SerializedName("matchFormat")
    private String matchFormat;

    @SerializedName("status")
    private String status;

    @SerializedName("state")
    private String state;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("team1")
    private Team team1;

    @SerializedName("team2")
    private Team team2;

    @SerializedName("score")
    private Score score;

    @SerializedName("venueInfo")
    private Venue venueInfo;

    public long getMatchId() { return matchId; }
    public String getSeriesName() { return seriesName == null ? "" : seriesName; }
    public String getMatchDesc() { return matchDesc == null ? "Match" : matchDesc; }
    public String getMatchFormat() { return matchFormat == null ? "" : matchFormat; }
    public String getStatus() { return status == null ? (state == null ? "Live" : state) : status; }
    public String getStartDate() { return startDate == null ? "0" : startDate; }
    public Team getTeam1() { return team1 == null ? new Team() : team1; }
    public Team getTeam2() { return team2 == null ? new Team() : team2; }
    public Score getScore() { return score == null ? new Score() : score; }
    public Venue getVenueInfo() { return venueInfo == null ? new Venue() : venueInfo; }

    // --- Manual setters, used only when reconstructing a Match from the Room cache ---
    public void setMatchIdRaw(long id) { this.matchId = id; }
    public void setSeriesName(String v) { this.seriesName = v; }
    public void setMatchDesc(String v) { this.matchDesc = v; }
    public void setStatus(String v) { this.status = v; }
    public void setStartDate(String v) { this.startDate = v; }
    public void setTeam1(Team v) { this.team1 = v; }
    public void setTeam2(Team v) { this.team2 = v; }
    public void setScore(Score v) { this.score = v; }
    public void setVenueInfo(Venue v) { this.venueInfo = v; }

    public String getTitle() {
        StringBuilder sb = new StringBuilder();
        if (!getSeriesName().isEmpty()) sb.append(getSeriesName()).append(" • ");
        sb.append(getMatchDesc());
        if (!getMatchFormat().isEmpty()) sb.append(" • ").append(getMatchFormat());
        return sb.toString();
    }
}
