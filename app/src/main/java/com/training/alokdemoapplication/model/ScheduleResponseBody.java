package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ScheduleResponseBody {
    @SerializedName("schedules")
    private List<ScheduleEntry> schedules;

    public List<ScheduleEntry> getSchedules() { return schedules; }
}
