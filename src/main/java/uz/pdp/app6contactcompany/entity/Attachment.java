package uz.pdp.app6contactcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor@Entity
public class Attachment {

    @Id@GeneratedValue
    private UUID id;

    private String originalName;

    private long size;

    private String contentType;

}
