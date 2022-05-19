package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.Payment;

import java.util.UUID;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> getAllBySimCardId(UUID simCard_id);

}
