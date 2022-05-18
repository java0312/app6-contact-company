package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.SimCard;

import java.util.UUID;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, UUID> {

    boolean existsByCodeAndNumber(String code, String number);

    boolean existsByCodeAndNumberAndIdNot(String code, String number, UUID id);

}
