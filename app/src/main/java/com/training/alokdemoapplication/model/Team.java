package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

public class Team {
    @SerializedName("teamId")
    private int teamId;

    @SerializedName("teamName")
    private String teamName;

    @SerializedName("teamSName")
    private String teamShortName;

    public int getTeamId() { return teamId; }
    public String getTeamName() { return teamName == null ? "TBA" : teamName; }
    public String getTeamShortName() { return teamShortName == null ? "" : teamShortName; }

    public void setTeamName(String v) { this.teamName = v; }
    public void setTeamShortName(String v) { this.teamShortName = v; }
}
