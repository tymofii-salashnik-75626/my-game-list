package game.project.service;

import game.project.dto.GameDto;
import game.project.model.Game;
import game.project.model.Genre;
import game.project.model.Developer;
import game.project.repository.DeveloperRepository;
import game.project.repository.GameRepository;
import game.project.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final DeveloperRepository developerRepository;
    
    public List<GameDto> getAllGames() {
        // 1. Достаем список сущностей из БД: gameRepository.findAll()
        // 2. Превращаем каждую сущность Game в GameDto с помощью метода mapToDto
        return gameRepository.findAll().stream()
                .map(game -> this.mapToDto(game))        
                // .map(this::mapToDto) 
                .collect(Collectors.toList());
    }

    // Метод 2: Найти одну игру по ID
    public GameDto getGameById(Long id) {
        // Ищем в БД. Если игры с    таким ID нет — выбрасываем понятную ошибку
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Игра с id " + id + " не найдена"));
        // Перекладываем данные из сущности в DTO и отдаем контроллеру
        return mapToDto(game);
    }

    // Получить список всех доступных жанров (для выпадающего списка на сайте)
    public List<String> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }

    // Добавить новую игру
    public GameDto createGame(GameDto dto) {
        Game game = new Game();
        game.setName(dto.getName());
        game.setGameYear(dto.getGameYear());
        game.setRating(dto.getRating());

        // 1. Жанры выбираем ТОЛЬКО из тех, что уже есть в базе
        if (dto.getGenres() != null) {
            Set<Genre> genres = dto.getGenres().stream()
                    .map(genreName -> genreRepository.findByName(genreName)
                            .orElseThrow(() -> new RuntimeException("Жанр '" + genreName + "' не найден в базе")))
                    .collect(Collectors.toSet());
            game.setGenres(genres);
        }

        // 2. Разработчика ищем в базе, а если его нет - создаем нового
        if (dto.getDevelopers() != null) {
            Set<Developer> developers = dto.getDevelopers().stream()
                    .map(devName -> developerRepository.findByName(devName)
                            .orElseGet(() -> {
                                Developer newDev = new Developer();
                                newDev.setName(devName);
                                return developerRepository.save(newDev);
                            }))
                    .collect(Collectors.toSet());
            game.setDevelopers(developers);
        }

        Game savedGame = gameRepository.save(game);
        return mapToDto(savedGame);
    }

    // Редактировать существующую игру
    public GameDto updateGame(Long id, GameDto dto) {
        // 1. Находим игру в базе по ID
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Игра с id " + id + " не найдена"));

        // 2. Обновляем основные поля
        game.setName(dto.getName());
        game.setGameYear(dto.getGameYear());
        game.setRating(dto.getRating());

        // 3. Обновляем жанры (строго из существующих в базе)
        if (dto.getGenres() != null) {
            Set<Genre> genres = dto.getGenres().stream()
                    .map(genreName -> genreRepository.findByName(genreName)
                            .orElseThrow(() -> new RuntimeException("Жанр '" + genreName + "' не найден в базе")))
                    .collect(Collectors.toSet());
            game.setGenres(genres);
        }

        // 4. Обновляем разработчиков (ищем или создаем новых)
        if (dto.getDevelopers() != null) {
            Set<Developer> developers = dto.getDevelopers().stream()
                    .map(devName -> developerRepository.findByName(devName)
                            .orElseGet(() -> {
                                Developer newDev = new Developer();
                                newDev.setName(devName);
                                return developerRepository.save(newDev);
                            }))
                    .collect(Collectors.toSet());
            game.setDevelopers(developers);
        }

        // 5. Сохраняем изменения и возвращаем обновленный DTO
        Game updatedGame = gameRepository.save(game);
        return mapToDto(updatedGame);
    }

    // Удалить игру по ID
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new RuntimeException("Игра с id " + id + " не найдена");
        }
        gameRepository.deleteById(id);
    }

    // Вспомогательный метод: перекладывает данные из Game в GameDto
    private GameDto mapToDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setGameYear(game.getGameYear());
        dto.setRating(game.getRating());

        // Достаем названия жанров и собираем в Set<String>
        dto.setGenres(game.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toSet()));

        // Достаем названия разработчиков и собираем в Set<String> 
        dto.setDevelopers(game.getDevelopers().stream()
                .map(Developer::getName)
                .collect(Collectors.toSet()));

        return dto;
    }
}