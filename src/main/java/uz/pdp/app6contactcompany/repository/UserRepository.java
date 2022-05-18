package uz.pdp.app6contactcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app6contactcompany.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmailOrUsername(String email, String username);

    Optional<User> findByEmailOrUsername(String email, String username);

    List<User> findAllByBranchId(Integer branch_id);

    boolean  existsByUsernameAndIdNot(String username, UUID id);
}
