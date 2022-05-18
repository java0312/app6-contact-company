package uz.pdp.app6contactcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimCardDto {
    private String code;
    private String number;
    private String passwordNumber;
    private UUID userId;
    private Integer balance;
}
