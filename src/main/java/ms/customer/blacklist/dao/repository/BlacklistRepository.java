package ms.customer.blacklist.dao.repository;

import ms.customer.blacklist.dao.entity.BlacklistEntity;
import ms.customer.blacklist.model.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlacklistRepository extends CrudRepository<BlacklistEntity, Long> {
    Optional<BlacklistEntity> findByCifAndStatus(String cif, Status status);

    List<BlacklistEntity> findByStatusAndExpireAtBefore(Status status, LocalDateTime time);
}
