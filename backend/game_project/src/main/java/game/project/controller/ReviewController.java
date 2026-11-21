package game.project.controller;

import game.project.dto.ReviewDto;
import game.project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games/{gameId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> getReviewsByGameId(@PathVariable Long gameId) {
        return reviewService.getReviewsByGameId(gameId);
    }

    @PostMapping
    public ReviewDto addReview(@PathVariable Long gameId, @RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(gameId, reviewDto);
    }
}