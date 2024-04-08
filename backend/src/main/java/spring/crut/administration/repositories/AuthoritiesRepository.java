package spring.crut.administration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.crut.administration.models.Authority;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
}
