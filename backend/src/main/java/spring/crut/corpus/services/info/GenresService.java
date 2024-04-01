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
}
