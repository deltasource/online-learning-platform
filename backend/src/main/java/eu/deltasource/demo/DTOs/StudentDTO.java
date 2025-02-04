package eu.deltasource.demo.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Details about the student")
public class StudentDTO {

    @Schema(description = "The unique identifier of the student", example = "1")
    private int id;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Schema(description = "The email address of the student", example = "student@example.com", required = true)
    private String email;

    @NotEmpty(message = "Full name is required.")
    @Schema(description = "The full name of the student", example = "John Doe", required = true)
    private String fullName;
}
