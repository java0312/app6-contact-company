package uz.pdp.app6contactcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor@Entity
public class AttachmentContent {

    @Id@GeneratedValue
    private UUID id;

    @OneToOne
    private Attachment attachment;

    private byte[] bytes;

}
