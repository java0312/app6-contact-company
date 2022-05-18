package uz.pdp.app6contactcompany.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegisterDTO {

    @Size(min = 5, max = 50, message = "size of firstName: min = 5, max = 50")
    @NotNull(message = "firstName is mandatory")
    private String firstName;

    @Size(min = 5, max = 50, message = "size of lastName: min = 5, max = 50")
    @NotNull(message = "lastName is mandatory")
    private String lastName;

    @NotNull(message = "email is mandatory")
    @Email(message = "email is wrong")
    private String email;

    @Size(min = 5, max = 20, message = "size of username: min = 5, max = 20")
    @NotNull(message = "username is mandatory")
    private String username;

    @Size(min = 5, max = 20, message = "password: min = 5, max = 20")
    @NotNull(message = "password must not be null")
    private String password;

    private boolean director;

    private Integer branchId;

}
