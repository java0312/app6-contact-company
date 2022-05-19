package uz.pdp.app6contactcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Call {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private SimCard fromSimCard;

    @ManyToOne(optional = false)
    private SimCard toSimCard;

    private Integer minute;

    @CreationTimestamp
    private Timestamp calledAt;

}
