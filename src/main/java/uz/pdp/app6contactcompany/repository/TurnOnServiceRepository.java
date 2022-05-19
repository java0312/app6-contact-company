package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.app6contactcompany.entity.TurnOnService;

import java.util.List;
import java.util.UUID;

@Repository
public interface TurnOnServiceRepository extends JpaRepository<TurnOnService, UUID> {

    boolean existsBySimCardIdAndServiceId(UUID simCard_id, UUID service_id);

    List<TurnOnService> findAllBySimCardId(UUID simCard_id);
}


