package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CommentaryResponse {
    @SerializedName("commentaryList")
    private List<CommentaryItem> commentaryList;

    public List<CommentaryItem> getCommentaryList() { return commentaryList; }
}
