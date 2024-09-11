package com.example.appdoctruyen.object;

public class HistoryItem {
    private int storyId;
    private int chapterId;
    private String storyTitle;
    private String readTime;

    public HistoryItem(int storyId, int chapterId, String storyTitle, String readTime) {
        this.storyId = storyId;
        this.chapterId = chapterId;
        this.storyTitle = storyTitle;
        this.readTime = readTime;
    }

    // Getters and Setters
    public int getStoryId() { return storyId; }
    public int getChapterId() { return chapterId; }
    public String getStoryTitle() { return storyTitle; }
    public String getReadTime() { return readTime; }
}

