package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

/** A single ball-by-ball / over commentary line. */
public class CommentaryItem {
    @SerializedName("commText")
    private String commentaryText;

    @SerializedName("overNumber")
    private String overNumber;

    @SerializedName("event")
    private String event;

    @SerializedName("timestamp")
    private long timestamp;

    public String getCommentaryText() { return commentaryText == null ? "" : commentaryText; }
    public String getOverNumber() { return overNumber == null ? "" : overNumber; }
    public String getEvent() { return event == null ? "" : event; }
    public long getTimestamp() { return timestamp; }
}
