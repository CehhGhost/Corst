package spring.crut.corpus.repositories.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import spring.crut.corpus.models.info.Info;

import java.util.Optional;

@NoRepositoryBean
public interface InfoRepository <T extends Info> extends JpaRepository<T, Long> {
    Optional<T> findByName(String name);
}
