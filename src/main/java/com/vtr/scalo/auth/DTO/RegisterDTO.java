package com.vtr.scalo.auth.DTO;

import com.vtr.scalo.users.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role, String name, Integer companyId) {
}
