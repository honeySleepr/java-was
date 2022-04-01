package model;

import java.time.LocalDate;

public class Article {
    private String userId;
    private String title;
    private LocalDate localDate;

    public Article(String userId, String title) {
        this.userId = userId;
        this.title = title;
        this.localDate = LocalDate.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
