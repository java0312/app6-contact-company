package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app6contactcompany.entity.UssdCode;
import uz.pdp.app6contactcompany.projection.CustomUssdCode;

import java.util.UUID;

@RepositoryRestResource(
        path = "ussdCode",
        excerptProjection = CustomUssdCode.class
)
public interface UssdCodeRepository extends JpaRepository<UssdCode, UUID> {

}
