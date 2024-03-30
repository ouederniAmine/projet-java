package com.example.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;

public class Hashtag {
    @JsonProperty("HashtagId")
    private String HashtagId;
    @JsonProperty("HashtagTweetsId")
    private ArrayList<String> HashtagTweetsId;

    public String getHashtagId() {
        return HashtagId;
    }

    public void setHashtagId(String hashtagId) {
        HashtagId = hashtagId;
    }

    public ArrayList<String> getTweetsId() {
        return HashtagTweetsId;
    }

    public void setHashtagTweetsId(ArrayList<String> tweetsId) {
        HashtagTweetsId = tweetsId;
    }

    public void setHashtagTweetsId(String[] tweetsId) {
        Collections.addAll(this.HashtagTweetsId, tweetsId);
    }

    public void setHashtagTweetsId(String tweetsId) {
        this.HashtagTweetsId.add(tweetsId);
    }
}
