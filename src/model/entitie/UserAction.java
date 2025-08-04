package model.entitie;

import java.time.LocalDateTime;

public class UserAction {

    private String description;
    private LocalDateTime dateAndTime;

    public UserAction(String description) {
        this.description = description;
        dateAndTime = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    
}
