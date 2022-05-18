package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.Service;

import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {

    boolean existsByNameAndCategoryId(String name, UUID category_id);

    boolean existsByUssdCodeId(UUID ussdCode_id);

    boolean existsByNameAndCategoryIdAndIdNot(String name, UUID category_id, UUID id);

    boolean existsByUssdCodeIdAndIdNot(UUID ussdCode_id, UUID id);

}
