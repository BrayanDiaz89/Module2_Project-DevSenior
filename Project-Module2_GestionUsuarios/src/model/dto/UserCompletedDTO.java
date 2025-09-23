package model.dto;

import model.enums.RoleUser;
import model.enums.StateUser;

public record UserCompletedDTO(
        int id,
        String name,
        String userName,
        RoleUser userRole,
        StateUser state
) {
}
