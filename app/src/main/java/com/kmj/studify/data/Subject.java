package com.kmj.studify.data;

public class Subject {
    private String name;
    private boolean isBookmarked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public Subject(String name, boolean isBookmarked) {
        this.name = name;
        this.isBookmarked = isBookmarked;
    }
}
