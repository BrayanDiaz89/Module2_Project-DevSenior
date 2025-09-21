package model.dto;

import model.ActionUser;

public record UserActionsDTO(
        String username,
        ActionUser[] actions
) {
}
