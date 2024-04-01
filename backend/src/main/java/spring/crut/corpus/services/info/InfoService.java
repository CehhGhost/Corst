package spring.crut.corpus.services.info;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.models.info.Info;
import spring.crut.corpus.repositories.info.InfoRepository;

import java.util.List;

public abstract class InfoService <T extends Info, R extends InfoRepository<T>> {
    @Autowired
    protected R repository;
    @Transactional
    public T create(String name) {
        name = name.toLowerCase();
        var check = repository.findByName(name);
        T info;
        if (check.isEmpty()) {
            info = repository.save(createInfo(name));
        } else {
            info = check.get();
        }
        return info;
    }
    @Transactional
    public List<T> getAll() {
        return repository.findAll();
    }

    protected abstract T createInfo(String name);
}
