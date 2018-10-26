package goldfish.express.repository;

import goldfish.express.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    UserEntity findByPhoneNumber(String phoneNumber);

}
