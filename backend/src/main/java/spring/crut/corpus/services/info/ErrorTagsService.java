package spring.crut.corpus.services.info;

import org.springframework.stereotype.Service;
import spring.crut.corpus.models.info.ErrorTag;
import spring.crut.corpus.repositories.info.ErrorTagsRepository;

@Service
public class ErrorTagsService extends InfoService<ErrorTag, ErrorTagsRepository> {
    @Override
    protected ErrorTag createInfo(String name) {
        var errorTag = new ErrorTag();
        errorTag.setName(name);
        return errorTag;
    }

    @Override
    protected void updateInfoForDocuments(Long id) {
        return;
    }
}
