package uz.pdp.app6contactcompany.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import uz.pdp.app6contactcompany.entity.template.AbsEntityIdSimCard;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Payment extends AbsEntityIdSimCard {

    @CreationTimestamp
    private Timestamp timestamp;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private double price;

}
