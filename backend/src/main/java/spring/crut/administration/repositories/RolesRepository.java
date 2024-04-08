package spring.crut.administration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.crut.administration.models.Role;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.authorities WHERE r.id = :roleId")
    Optional<Role> findByIdWithAuthorities(@Param("roleId") Long roleId);
}
