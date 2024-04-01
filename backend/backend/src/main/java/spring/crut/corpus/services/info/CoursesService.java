package spring.crut.corpus.services.info;

import org.springframework.stereotype.Service;
import spring.crut.corpus.models.info.Course;
import spring.crut.corpus.repositories.info.CoursesRepository;

@Service
public class CoursesService extends InfoService<Course, CoursesRepository> {
    @Override
    protected Course createInfo(String name) {
        var course = new Course();
        course.setName(name);
        return course;
    }
}
