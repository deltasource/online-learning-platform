package eu.deltasource.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents a student in the system")
public class Student {

    @Schema(description = "The unique identifier of the student", example = "1")
    private int id;

    @Schema(description = "The email address of the student", example = "student@example.com")
    private String email;

    @Schema(description = "The full name of the student", example = "John Doe")
    private String fullName;

}
