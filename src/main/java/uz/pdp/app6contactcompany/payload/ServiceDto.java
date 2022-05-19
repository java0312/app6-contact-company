package uz.pdp.app6contactcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.app6contactcompany.entity.Category;
import uz.pdp.app6contactcompany.entity.UssdCode;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private String name;
    private UUID categoryId;
    private Integer period; //day
    private String info;
    private UUID ussdCodeId;
    private boolean residueAdds = true;
    private boolean active = true;
    private double price;
}
