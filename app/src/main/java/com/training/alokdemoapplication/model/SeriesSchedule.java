package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SeriesSchedule {
    @SerializedName("seriesName")
    private String seriesName;

    @SerializedName("matchInfo")
    private List<Match> matchInfo;

    public String getSeriesName() { return seriesName == null ? "" : seriesName; }
    public List<Match> getMatchInfo() { return matchInfo; }
}
