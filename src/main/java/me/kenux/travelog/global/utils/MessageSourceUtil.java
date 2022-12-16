package me.kenux.travelog.global.utils;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceUtil {

    private final MessageSource messageSource;

    public String getRoleMessage(UserRole role) {
        String code = "member.role.user";
        if (role.equals(UserRole.ADMIN)) {
            code = "member.role.admin";
        }
        return messageSource.getMessage(code, null, null);
    }
}
