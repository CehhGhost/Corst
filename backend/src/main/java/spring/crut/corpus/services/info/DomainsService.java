package spring.crut.corpus.services.info;

import org.springframework.stereotype.Service;
import spring.crut.corpus.models.info.Domain;
import spring.crut.corpus.repositories.info.DomainsRepository;

@Service
public class DomainsService extends InfoService<Domain, DomainsRepository>{
    @Override
    protected Domain createInfo(String name) {
        var domain = new Domain();
        domain.setName(name);
        return domain;
    }

    @Override
    protected void updateInfoForDocuments(Long id) {
        var replacement = repository.findByName("unknown");
        if (replacement.isEmpty()) {
            throw new IllegalArgumentException("Something is wrong, cant find default Domain");
        }
        repository.updateDomainForDocuments(id, replacement.get());
    }
}
