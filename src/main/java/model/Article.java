package model;

import java.time.LocalDate;

public class Article {
    private String userId;
    private String content;
    private LocalDate localDate;

    public Article(String userId, String content) {
        this.userId = userId;
        this.content = content;
        this.localDate = LocalDate.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
