package uz.pdp.app6contactcompany.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class SwitchTariffDto {
    private UUID tariffId;
    private UUID simCardId;
}
