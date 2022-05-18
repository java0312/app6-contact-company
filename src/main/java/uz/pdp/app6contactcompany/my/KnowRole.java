package uz.pdp.app6contactcompany.my;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.app6contactcompany.entity.Role;
import uz.pdp.app6contactcompany.entity.User;
import uz.pdp.app6contactcompany.entity.enums.RoleName;

import java.util.Set;

/*
 * Rollar boyicha muamo bolganligi uchun va harbir harakatni bajarganda rolini tekshirish kerak bolganligi uchun
 * bu classni ochdim
 * */
@Component
public class KnowRole {

    //__DIRECTOR__
    public boolean isDirector() {
        return existRole(RoleName.DIRECTOR);
    }

    public boolean isDirector(Set<Role> roles){
        return existRole(roles, RoleName.DIRECTOR);
    }


    //__MANAGER
    public boolean isManager() {
        return isBranchManager() || isNumbersManager() || isEmployeeManager();
    }

    public boolean isManager(Set<Role> roles){
        return isBranchManager(roles) || isNumberManager(roles) || isEmployeeManager(roles);
    }

    //__BRANCH_MANAGER__
    public boolean isBranchManager() {
        return existRole(RoleName.BRANCH_MANAGER);
    }

    public boolean isBranchManager(Set<Role> roles){
        return existRole(roles, RoleName.BRANCH_MANAGER);
    }


    //__NUMBERS_MANAGER__
    public boolean isNumbersManager() {
        return existRole(RoleName.NUMBERS_MANAGER);
    }

    public boolean isNumberManager(Set<Role> roles){
        return existRole(roles, RoleName.NUMBERS_MANAGER);
    }


    //__EMPLOYEE_MANAGER__
    public boolean isEmployeeManager() {
        return existRole(RoleName.EMPLOYEE);
    }

    public boolean isEmployeeManager(Set<Role> roles){
        return existRole(roles, RoleName.EMPLOYEE_MANAGER);
    }


    //__BRANCH_LEADER__
    public boolean isBranchLeader() {
        return existRole(RoleName.BRANCH_LEADER);
    }

    public boolean isBranchLeader(Set<Role> roles){
        return existRole(roles, RoleName.BRANCH_LEADER);
    }


    //__EMPLOYEE__
    public boolean isEmployee() {
        return existRole(RoleName.EMPLOYEE);
    }

    public boolean isEmployee(Set<Role> roles){
        return existRole(roles, RoleName.EMPLOYEE);
    }


    //__SUBSCRIBER__
    public boolean isSubscriber() {
        return existRole(RoleName.SUBSCRIBER);
    }

    public boolean isSubscriber(Set<Role> roles){
        return existRole(roles, RoleName.SUBSCRIBER);
    }


    //__IS NOBODY IN AUTHENTICATION
    public boolean isNobody() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }

    //__USER IN AUTHENTICATION
    public User getAuthUser() {
        if (!isNobody())
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }

    //__ROLES OF USER IN AUTHENTICATION
    public Set<Role> getAuthUserRoles() {
        User authUser = getAuthUser();
        if (authUser != null)
            return authUser.getRoles();
        return null;
    }






    private boolean existRole(RoleName roleName){
        Set<Role> authUserRoles = getAuthUserRoles();
        if (authUserRoles == null)
            return false;
        for (Role authUserRole : authUserRoles) {
            if (authUserRole.getRoleName().equals(roleName))
                return true;
        }
        return false;
    }

    private boolean existRole(Set<Role> roles, RoleName roleName){
        for (Role role : roles) {
            if (role.getRoleName().equals(roleName))
                return true;
        }
        return false;
    }

}
