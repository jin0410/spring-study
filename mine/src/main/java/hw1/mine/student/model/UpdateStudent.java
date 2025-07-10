package hw1.mine.student.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStudent {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String dept;

}
