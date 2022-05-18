package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.LoginDTO;
import uz.pdp.app6contactcompany.payload.RegisterDTO;
import uz.pdp.app6contactcompany.service.AuthService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * * * * * * *
     * REGISTER *
     * * * * * * *
     */
    @Transactional
    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO){
        ApiResponse apiResponse = authService.registerUser(registerDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * * * * * * * * *
     * VERIFY EMAIL *
     * * * * * * * * *
     */
    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code){
        ApiResponse apiResponse = authService.verifyEmail(email, code);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * * * * * * * * * * *
     * LOGIN TO SYSTEM *
     * * * * * * * * * * *
     */
    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@Valid @RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse = authService.loginToSystem(loginDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }









    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * This method prints message of @NotNull in validation  *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
