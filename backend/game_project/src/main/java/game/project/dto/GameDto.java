package game.project.dto;

import lombok.Data;
import java.util.Set;

@Data
public class GameDto {
    private Long id;
    private String name;
    private Integer gameYear;
    private Integer rating;
    private Set<String> genres;     
    private Set<String> developers;  
}