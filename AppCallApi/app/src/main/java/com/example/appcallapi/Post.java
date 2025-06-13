package com.example.appcallapi;

public class Post {
    private int id;
    private String title;
    private String body;
    private int userId; // Thêm trường userId để khớp với JSON nếu cần, dù không dùng trong hiển thị

    // Constructor
    public Post(int id, String title, String body, int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getUserId() {
        return userId;
    }

    // Setters (nếu cần, nhưng thường không cần cho lớp model dữ liệu đọc)
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}