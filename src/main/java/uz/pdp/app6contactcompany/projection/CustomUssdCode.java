package uz.pdp.app6contactcompany.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.app6contactcompany.entity.UssdCode;

import java.util.UUID;

@Projection(types = UssdCode.class)
public interface CustomUssdCode {

    UUID getId();

    String getCode();

    String getInfo();

}
