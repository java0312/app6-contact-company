package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app6contactcompany.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    boolean existsByNameAndRegion_Id(String name, Integer region_id);

    boolean existsByNameAndRegion_IdAndIdNot(String name, Integer region_id, Integer id);

}
