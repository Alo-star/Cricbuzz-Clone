package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LiveMatchesResponse {
    @SerializedName("response")
    private List<Match> response;

    public List<Match> getMatches() { return response; }
}
