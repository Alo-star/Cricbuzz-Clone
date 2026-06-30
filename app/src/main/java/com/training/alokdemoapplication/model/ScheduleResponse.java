package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

public class ScheduleResponse {
    @SerializedName("response")
    private ScheduleResponseBody response;

    public ScheduleResponseBody getResponseBody() { return response; }
}
