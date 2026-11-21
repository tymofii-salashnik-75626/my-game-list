package game.project.service;

import game.project.dto.ReviewDto;
import game.project.model.Game;
import game.project.model.Review;
import game.project.repository.GameRepository;
import game.project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;

    // Метод 1: Получить все отзывы для конкретной игры
    public List<ReviewDto> getReviewsByGameId(Long gameId) {
        // Ищем в базе все отзывы, привязанные к этой игре, и мапим их в ReviewDto
        return reviewRepository.findByGameId(gameId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Метод 2: Сохранить новый отзыв к игре
    public ReviewDto addReview(Long gameId, ReviewDto dto) {
        // 1. Проверяем, существует ли сама игра, к которой пишут отзыв
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Игра с id " + gameId + " не найдена"));

        // 2. Создаем новую сущность Review для базы данных
        Review review = new Review();
        review.setGame(game); // Привязываем отзыв к найденной игре
        review.setAuthorName(dto.getAuthorName());
        review.setGrade(dto.getGrade());
        review.setReviewText(dto.getReviewText());

        // 3. Сохраняем отзыв в БД через репозиторий
        Review saved = reviewRepository.save(review);

        // 4. Возвращаем сохраненный отзыв в формате DTO
        return mapToDto(saved);
    }

    // Вспомогательный метод: перекладывает данные из Review в ReviewDto
    private ReviewDto mapToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setAuthorName(review.getAuthorName());
        dto.setGrade(review.getGrade());
        dto.setReviewText(review.getReviewText());
        return dto;
    }
}