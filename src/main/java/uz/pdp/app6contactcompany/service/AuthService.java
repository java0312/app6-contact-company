package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.Branch;
import uz.pdp.app6contactcompany.entity.User;
import uz.pdp.app6contactcompany.entity.enums.RoleName;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.LoginDTO;
import uz.pdp.app6contactcompany.payload.RegisterDTO;
import uz.pdp.app6contactcompany.repository.BranchRepository;
import uz.pdp.app6contactcompany.repository.RoleRepository;
import uz.pdp.app6contactcompany.repository.UserRepository;
import uz.pdp.app6contactcompany.security.JwtProvider;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KnowRole knowRole;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail);
        return optionalUser.orElse(null);
    }


    /**
     * ****************** * *
     * __REGISTER USER__   *
     * ****************** * *
     */
    public ApiResponse registerUser(RegisterDTO registerDTO) {

        boolean existsByEmail = userRepository.existsByEmail(registerDTO.getEmail());
        if (existsByEmail)
            return new ApiResponse("This email already registered!", false);

        boolean existsByUserName = userRepository.existsByUsername(registerDTO.getUsername());
        if (existsByUserName)
            return new ApiResponse("This username already taken!", false);

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        if(registerDTO.getBranchId() != null) {
            Optional<Branch> optionalBranch = branchRepository.findById(registerDTO.getBranchId());
            if (optionalBranch.isEmpty())
                return new ApiResponse("Branch not found!", false);
            user.setBranch(optionalBranch.get());
        }

        RoleName roleName = RoleName.SUBSCRIBER;
        if (registerDTO.isDirector())
            roleName = RoleName.DIRECTOR;
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(roleName)));


        user.setEmailCode(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);

        boolean sendEmail = sendEmail(savedUser.getEmail(), savedUser.getEmailCode());
        if (sendEmail)
            return new ApiResponse("You successfully registered!", true);

        return new ApiResponse("Error confirm account!", false);
    }

    private boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Confirm account");
            mailMessage.setText(
                    "Confirm your account!\n" +
                            "http://localhost:9090/api/auth/verifyEmail?email=" + sendingEmail +
                            "&code=" + emailCode
            );
            mailMessage.setFrom("farhodovalisher20@gmail.com");
            mailMessage.setTo(sendingEmail);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * **************** * *
     * __VERIFY EMAIL__   *
     * **************** * *
     */
    public ApiResponse verifyEmail(String email, String code) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        User user = optionalUser.get();
        String emailCode = user.getEmailCode();
        if (!emailCode.equals(code))
            return new ApiResponse("Email code is wrong!", false);

        user.setEmailCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return new ApiResponse("Your account is confirmed!", true);
    }


    /**
     * ****************** * *
     * __LOGIN TO SYSTEM__  *
     * ****************** * *
     */
    public ApiResponse loginToSystem(LoginDTO loginDTO) {

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsernameOrEmail(),
                            loginDTO.getPassword()
                    )
            );
            User principal = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(loginDTO.getUsernameOrEmail(), principal.getRoles());
            return new ApiResponse("your token!", true, token);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(
                    "you have not registered yet!",
                    false,
                    "http://localhost:9090/api/auth/register"
            );
        }

    }


}

/*
 *
 * */










