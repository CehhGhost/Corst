package spring.crut.corpus.services.info;

import org.springframework.stereotype.Service;
import spring.crut.corpus.models.info.AcademicMajor;
import spring.crut.corpus.repositories.info.AcademicMajorsRepository;

@Service
public class AcademicMajorsService extends InfoService<AcademicMajor, AcademicMajorsRepository>{

    @Override
    protected AcademicMajor createInfo(String name) {
        var academicMajor = new AcademicMajor();
        academicMajor.setName(name);
        return academicMajor;
    }
}
