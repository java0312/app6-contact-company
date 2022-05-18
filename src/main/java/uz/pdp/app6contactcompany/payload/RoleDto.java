package uz.pdp.app6contactcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/*
* Foydalanuvchiga rollarni berish uchun
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private UUID id;
    private Set<String> roles;
}

/*
* "lastName": "Farhodov2",
    "firstName": "Alisher2",
    "password": "123452",
    "email": "aiclbwe@gmail.com",
    "username": "alisher2"
* */
