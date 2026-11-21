package game.project.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String authorName;
    private Integer grade;
    private String reviewText;
}