package com.obiangetfils.kermashop.models;

import java.util.List;

public class StoryOBJ {

    private String storyId;
    private String storyNameSender;
    private String storyDate;
    private String storyTime;
    private List<String> imageUri;

    public StoryOBJ(String storyId, String storyNameSender, String storyDate, String storyTime, List<String> imageUri) {
        this.storyId = storyId;
        this.storyNameSender = storyNameSender;
        this.storyDate = storyDate;
        this.storyTime = storyTime;
        this.imageUri = imageUri;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryNameSender() {
        return storyNameSender;
    }

    public void setStoryNameSender(String storyNameSender) {
        this.storyNameSender = storyNameSender;
    }

    public String getStoryDate() {
        return storyDate;
    }

    public void setStoryDate(String storyDate) {
        this.storyDate = storyDate;
    }

    public String getStoryTime() {
        return storyTime;
    }

    public void setStoryTime(String storyTime) {
        this.storyTime = storyTime;
    }

    public List<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(List<String> imageUri) {
        this.imageUri = imageUri;
    }
}
