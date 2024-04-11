package spring.crut.corpus.repositories.info;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.models.info.Course;

@Repository
public interface CoursesRepository extends InfoRepository<Course>{
    @Modifying
    @Transactional
    @Query("UPDATE Document d SET d.authorsCourse = :newInfo WHERE d.authorsCourse.id = :infoId")
    void updateCourseForDocuments(@Param("infoId") Long infoId, @Param("newInfo") Course newInfo);
}
