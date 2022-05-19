package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app6contactcompany.entity.Tariff;
import uz.pdp.app6contactcompany.projection.CustomTariff;

import java.util.UUID;

@RepositoryRestResource(
        path = "tariff",
        excerptProjection = CustomTariff.class
)
public interface TariffRepository extends JpaRepository<Tariff, UUID> {
}
