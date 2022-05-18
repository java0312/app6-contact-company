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
public class Service {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    @Column(nullable = false)
    private Integer period; //day

    @Column(nullable = false)
    private String info;

    @OneToOne(optional = false)
    private UssdCode ussdCode;

    private boolean residueAdds = true;

    private boolean active = true;

}