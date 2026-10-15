package game.project.repository;

import game.project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ReviewRepository  extends  JpaRepository<Review,Long>{
    // SELECT * FROM review WHERE game_id = ?
    List<Review> findByGameId(Long gameId);
}
