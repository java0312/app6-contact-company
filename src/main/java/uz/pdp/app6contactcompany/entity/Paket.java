package uz.pdp.app6contactcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.app6contactcompany.entity.enums.PaketType;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Paket {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private PaketType paketType;

    private double price;

    private Integer period;

    @OneToOne
    private UssdCode ussdCode;

}
