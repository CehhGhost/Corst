package spring.crut.corpus.repositories.info;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.models.info.Genre;

@Repository
public interface GenresRepository extends InfoRepository<Genre> {
    @Modifying
    @Transactional
    @Query("UPDATE Document d SET d.genre = :newInfo WHERE d.genre.id = :infoId")
    void updateGenreForDocuments(@Param("infoId") Long infoId, @Param("newInfo") Genre newInfo);
}
