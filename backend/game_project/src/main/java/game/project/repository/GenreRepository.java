package game.project.repository;

import game.project.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    // SELECT * FROM genre WHERE name = 'имя жанра'
    Optional<Genre> findByName(String name);
}