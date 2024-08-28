package com.example.appdoctruyen.object;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int id;
    private String name;
    private String content;
    private int storyId;

    // Constructor
    public Chapter(int id, String name, String content, int storyId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.storyId = storyId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getStoryId() { return storyId; }
    public void setStoryId(int storyId) { this.storyId = storyId; }
}




