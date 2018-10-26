package goldfish.express.repository;

import goldfish.express.entity.OperatorTelegramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorTelegramEntity, Long> {
}
