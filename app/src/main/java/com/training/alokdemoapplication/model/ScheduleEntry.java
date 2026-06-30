package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

public class ScheduleEntry {
    @SerializedName("scheduleAdWrapper")
    private ScheduleAdWrapper scheduleAdWrapper;

    public ScheduleAdWrapper getScheduleAdWrapper() { return scheduleAdWrapper; }
}
