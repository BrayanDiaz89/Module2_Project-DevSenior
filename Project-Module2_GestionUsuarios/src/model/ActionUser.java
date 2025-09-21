package model;

import java.time.LocalDateTime;

public class ActionUser {
    private LocalDateTime fecha;
    private String description;

    public ActionUser(String description) {
        fecha = LocalDateTime.now();
        this.description = description;
    }
}
