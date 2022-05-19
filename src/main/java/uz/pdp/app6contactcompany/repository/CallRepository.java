package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.Call;

import java.util.List;
import java.util.UUID;

@Repository
public interface CallRepository extends JpaRepository<Call, UUID> {

    List<Call> findAllByFromSimCard_IdOrToSimCard_Id(UUID fromSimCard_id, UUID toSimCard_id);

}

