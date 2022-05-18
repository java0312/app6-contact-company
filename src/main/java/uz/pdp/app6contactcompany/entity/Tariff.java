package uz.pdp.app6contactcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tariff {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String info;

    private boolean forLegalPerson;

    private double price;

    private Integer period; //day count

    /*
    * In Tariff
    * */
    private Double megabyteIn;

    private Integer minuteInNet; //count

    private Integer minuteOutNet; //count

    private Integer sms; //count

    /*
    * Out Tariff price
    * */

    private double megabytePrice;

    private double minutePriceInNet;

    private double minutePriceOutNet;

    private double smsPrice;

    @OneToOne
    private UssdCode ussdCode;

}
