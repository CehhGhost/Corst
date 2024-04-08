package spring.crut.administration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.administration.models.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsUserByUsername(String username);
}
