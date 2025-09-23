package model.dto;

import model.enums.RoleUser;

public record UserGenericDTO(
        int id,
        String name,
        RoleUser userRole
) {
}
