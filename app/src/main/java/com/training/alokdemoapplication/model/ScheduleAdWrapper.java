package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ScheduleAdWrapper {
    @SerializedName("date")
    private String date;

    @SerializedName("matchScheduleList")
    private List<SeriesSchedule> matchScheduleList;

    public String getDate() { return date == null ? "" : date; }
    public List<SeriesSchedule> getMatchScheduleList() { return matchScheduleList; }
}
