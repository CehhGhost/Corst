package spring.crut.corpus.repositories.info;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.models.info.AcademicMajor;

@Repository
public interface AcademicMajorsRepository extends InfoRepository<AcademicMajor> {
    @Modifying
    @Transactional
    @Query("UPDATE Document d SET d.authorsAcademicMajor = :newInfo WHERE d.authorsAcademicMajor.id = :infoId")
    void updateAcademicMajorForDocuments(@Param("infoId") Long infoId, @Param("newInfo") AcademicMajor newInfo);
}
