package hw1.mine.student.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStudent {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String dept;
}
