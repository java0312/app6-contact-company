package uz.pdp.app6contactcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotNull(message = "must not be empty")
    private String usernameOrEmail;

    @NotNull(message = "password must not be empty")
    private String password;
}
