package uz.pdp.app6contactcompany.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app6contactcompany.projection.CustomTariff;

@RepositoryRestResource(
        path = "tariff",
        excerptProjection = CustomTariff.class
)
public interface TariffRepository {
}
