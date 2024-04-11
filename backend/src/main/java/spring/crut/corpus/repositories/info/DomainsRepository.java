package spring.crut.corpus.repositories.info;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.models.info.Domain;

@Repository
public interface DomainsRepository extends InfoRepository<Domain>{
    @Modifying
    @Transactional
    @Query("UPDATE Document d SET d.domain = :newInfo WHERE d.domain.id = :infoId")
    void updateDomainForDocuments(@Param("infoId") Long infoId, @Param("newInfo") Domain newInfo);
}
