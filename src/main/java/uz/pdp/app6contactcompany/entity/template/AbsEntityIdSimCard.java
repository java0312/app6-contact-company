package uz.pdp.app6contactcompany.entity.template;

import lombok.Data;
import uz.pdp.app6contactcompany.entity.SimCard;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public class AbsEntityIdSimCard {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private SimCard simCard;

}
