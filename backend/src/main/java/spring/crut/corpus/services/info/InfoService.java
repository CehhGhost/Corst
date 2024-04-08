package spring.crut.corpus.services.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import spring.crut.corpus.models.info.Info;
import spring.crut.corpus.repositories.info.InfoRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class InfoService <T extends Info, R extends InfoRepository<T>> {
    @Autowired
    protected R repository;
    @Transactional
    public T create(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
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
    @Transactional
    public List<String> getAllNames() {
        List<String> result = new ArrayList<>();
        for (var info : repository.findAll()) {
            result.add(info.getName());
        }
        return result;
    }

    protected abstract T createInfo(String name);

    @Transactional
    public Set<T> getAllByNames(List<String> errorTagsNames) {
        Set<T> result = new HashSet<>();
        for (var name : errorTagsNames) {
            name = name.toLowerCase();
            var info = repository.findByName(name);
            if (info.isEmpty()) {
                throw new IllegalArgumentException("No info with such name!");
            }
            result.add(info.get());
        }
        return result;
    }
}
