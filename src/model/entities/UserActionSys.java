package model.entities;

import java.time.LocalDateTime;

public class UserActionSys {
    private String description;
    private LocalDateTime dateTime;

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public UserActionSys(String description) {
        this.description = description;
        dateTime = LocalDateTime.now();
    }
}
