package spring.crut.corpus.services.info;

import org.springframework.stereotype.Service;
import spring.crut.corpus.models.info.Genre;
import spring.crut.corpus.repositories.info.GenresRepository;

@Service
public class GenresService extends InfoService<Genre, GenresRepository> {
    @Override
    protected Genre createInfo(String name) {
        var genre = new Genre();
        genre.setName(name);
        return genre;
    }

    @Override
    protected void updateInfoForDocuments(Long id) {
        var replacement = repository.findByName("unknown");
        if (replacement.isEmpty()) {
            throw new IllegalArgumentException("Something is wrong, cant find default Genre");
        }
        repository.updateGenreForDocuments(id, replacement.get());
    }
}
