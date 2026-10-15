package game.project.repository;

import game.project.model.Genre; 
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {

}