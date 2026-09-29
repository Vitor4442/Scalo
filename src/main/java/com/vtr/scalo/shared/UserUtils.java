package com.vtr.scalo.shared;

import com.vtr.scalo.users.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static User getCurentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        return (User) authentication.getPrincipal();
    }

    public static Integer getCurrentCompanyId(){
        return getCurentUser().getCompany().getId();
    }
}
