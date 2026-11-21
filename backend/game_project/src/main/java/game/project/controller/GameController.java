package game.project.controller;

import game.project.dto.GameDto;
import game.project.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<GameDto> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public GameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    // Получить список доступных жанров: GET /api/games/genres
    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return gameService.getAllGenres();
    }

    // Добавить новую игру: POST /api/games
    @PostMapping
    public GameDto createGame(@RequestBody GameDto gameDto) {
        return gameService.createGame(gameDto);
    }

    // Редактировать игру: PUT /api/games/{id}
    @PutMapping("/{id}")
    public GameDto updateGame(@PathVariable Long id, @RequestBody GameDto gameDto) {
        return gameService.updateGame(id, gameDto);
    }

    // Удалить игру: DELETE /api/games/{id}
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}