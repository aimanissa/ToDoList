package com.myprojects.android.todolist;

import java.util.UUID;

public class ToDo {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private boolean mSolved;

    public ToDo() {
        this(UUID.randomUUID());
    }

    public ToDo(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
