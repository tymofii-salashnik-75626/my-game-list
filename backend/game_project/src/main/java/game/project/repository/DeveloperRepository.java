package game.project.repository;

import game.project.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    //чтоб не было дубликатов студий 
    Optional<Developer> findByName(String name);
}