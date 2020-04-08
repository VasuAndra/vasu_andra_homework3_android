package com.example.android_tema3_vasu_andra.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ToDo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public ToDo(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public ToDo() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ToDo fromJSON(JSONObject itemJSON) throws JSONException {

        int id = itemJSON.getInt("id");
        int userId = itemJSON.getInt("userId");
        String title = itemJSON.getString("title");
        Boolean completed = itemJSON.getBoolean("completed");

        ToDo toDo = new ToDo(id, userId, title, completed);
        return toDo;
    }
}
