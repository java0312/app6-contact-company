package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.Role;
import uz.pdp.app6contactcompany.entity.User;
import uz.pdp.app6contactcompany.entity.enums.RoleName;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.RoleDto;
import uz.pdp.app6contactcompany.payload.UserDto;
import uz.pdp.app6contactcompany.repository.RoleRepository;
import uz.pdp.app6contactcompany.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    KnowRole knowRole;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * * * * * * * * * *
     * Give role to user  *
     * * * * * * * * * *
     **/
    public ApiResponse setRolesToUser(RoleDto roleDto) {

        //rollarning string toplami
        Set<String> stringRoles = roleDto.getRoles();

        //authentication faqat ozining rolidan kichikini qo'shaoladi
        filterRoles(stringRoles);

        Optional<User> optionalUser = userRepository.findById(roleDto.getId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        User user = optionalUser.get();
        Set<Role> roles = user.getRoles();
        for (String stringRole : stringRoles) {
            roles.add(roleRepository.findByRoleName(RoleName.valueOf(stringRole)));
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse("Roles given!", true);
    }

    private Set<String> filterRoles(Set<String> roles) {
        if (knowRole.isBranchManager()) {
            for (String role : roles) {
                boolean director = role.equals(RoleName.DIRECTOR.toString());
                boolean manager = role.equals(RoleName.BRANCH_MANAGER.toString()) || role.equals(RoleName.NUMBERS_MANAGER.toString()) || role.equals(RoleName.EMPLOYEE_MANAGER.toString());
                if (director || manager)
                    roles.remove(role);
            }
        } else if (knowRole.isBranchLeader()) {
            for (String role : roles) {
                boolean notEmployee = !role.equals(RoleName.EMPLOYEE.toString());
                if (notEmployee)
                    roles.remove(role);
            }
        } else if (knowRole.isDirector())
            return roles;

        roles = new HashSet<>();
        roles.add("SUBSCRIBER");
        return roles;
    }


    /**
     * READ
     */

//     * Hamma userlarni korish
//     * director va managerlar uchun
    public List<User> getAllUsers() {
        if (knowRole.isDirector())
            return userRepository.findAll();
        return null;
    }

    //     * branch_leader dan yuqori rollar uchun
    public List<User> getAllUsersByBranchId(Integer branchId) {
        if (knowRole.isDirector() || knowRole.isManager())
            return userRepository.findAllByBranchId(branchId);

        if (knowRole.isBranchLeader()) {
            if (knowRole.getAuthUser().getBranch().getId() == branchId)
                return userRepository.findAllByBranchId(branchId);
        }

        return null;
    }

    public User getUser(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return null;

        if (knowRole.isEmployee() || knowRole.isSubscriber()) {
            if (knowRole.getAuthUser().getId().equals(id))
                return optionalUser.get();
            else
                return null;
        }

        return optionalUser.get();
    }


    /**
     * UPDATE
     */
    public ApiResponse editUser(UUID id, UserDto userDto) {

        boolean exists = userRepository.existsByUsernameAndIdNot(userDto.getUsername(), id);
        if (exists) {
            return new ApiResponse("This username exist!", false);
        }

        if (knowRole.isDirector() || knowRole.isManager() || knowRole.getAuthUser().getId().equals(id)){
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return new ApiResponse("User not found!", false);
            }
            User user = optionalUser.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return new ApiResponse("User edited!", true);
        }

        return new ApiResponse("You cannot edited!", false);
    }

    /*
     * delete -> enabled = false
     * */
    public ApiResponse deleteUser(UUID id) {
        User authUser = knowRole.getAuthUser();
        if (authUser.getId().equals(id)) {
            authUser.setEnabled(false);
            userRepository.save(authUser);
            return new ApiResponse("user deleted!", true);
        }
        if (knowRole.isDirector()) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty())
                return new ApiResponse("User not found!", false);
            User user = optionalUser.get();
            user.setEnabled(false);
            userRepository.save(user);
        }
        return new ApiResponse("You cannot delete user", false);
    }
}











