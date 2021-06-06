package uz.pdp.muharrir.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.muharrir.entity.User;
import uz.pdp.muharrir.exceptions.ForbiddenException;

@Component
@Aspect
public class CheckPermissonExecuter {

    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermissonMethod(CheckPermission checkPermission) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean exist = false;

        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.value())) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            throw new ForbiddenException(checkPermission.value(), "Nelzya");
        }
    }
}
