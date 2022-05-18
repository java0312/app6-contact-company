package uz.pdp.app6contactcompany.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.app6contactcompany.entity.Tariff;
import uz.pdp.app6contactcompany.entity.UssdCode;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.util.UUID;

@Projection(types = Tariff.class)
public interface CustomTariff {
    UUID getId();

    String getName();

    String getInfo();

    boolean isForLegalPerson();

    double getPrice();

    Integer getPeriod(); //day count

    Double getMegabyteIn();

    Integer getMinuteInNet(); //count

    Integer getMinuteOutNet(); //count

    Integer getSms(); //count

    double getMegabytePrice();

    double getMinutePriceInNet();

    double getMinutePriceOutNet();

    double getSmsPrice();

    UssdCode getUssdCode();

}
