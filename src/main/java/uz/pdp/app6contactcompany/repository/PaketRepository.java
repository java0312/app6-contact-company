package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.Paket;
import uz.pdp.app6contactcompany.projection.CustomPaket;

import java.util.UUID;

@RepositoryRestResource(
        path = "paket",
        excerptProjection = CustomPaket.class
)
public interface PaketRepository extends JpaRepository<Paket, UUID> {

}
