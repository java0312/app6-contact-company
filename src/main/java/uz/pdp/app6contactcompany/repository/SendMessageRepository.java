package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.SendMessage;

import java.util.List;
import java.util.UUID;

@Repository
public interface SendMessageRepository extends JpaRepository<SendMessage, UUID> {

    List<SendMessage> findAllByFromSimCard_IdOrToSimCard_Id(UUID fromSimCard_id, UUID toSimCard_id);

}
