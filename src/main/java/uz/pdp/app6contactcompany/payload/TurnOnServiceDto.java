package uz.pdp.app6contactcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnOnServiceDto {
    private UUID simCardId;
    private UUID serviceId;
}
