package eu.deltasource.demo.service;

import eu.deltasource.demo.DTOs.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class StudentServiceTest {

    private final StudentService studentService = new StudentService();

    @Test
    void givenNewStudent_whenCreatingStudent_thenStudentIsCreatedSuccessfully() {
        // Given
        StudentDTO newStudent = new StudentDTO(1, "test@test.com", "Test Student");

        // When
        Optional<StudentDTO> result = studentService.createStudent(newStudent);

        // Then
        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(createdStudent -> {
            assertThat(createdStudent.getId()).isEqualTo(newStudent.getId());
            assertThat(createdStudent.getEmail()).isEqualTo(newStudent.getEmail());
            assertThat(createdStudent.getFullName()).isEqualTo(newStudent.getFullName());
        });
        assertThat(studentService.getStudentByEmail("test@test.com")).isPresent().contains(newStudent);
    }

    @Test
    void givenExistingStudentEmail_whenCreatingStudent_thenCreationFails() {
        // Given
        StudentDTO existingStudent = new StudentDTO(1, "test@test.com", "Existing Student");
        studentService.createStudent(existingStudent);

        // When
        Optional<StudentDTO> result = studentService.createStudent(existingStudent);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void givenExistingStudent_whenGettingStudentByEmail_thenCorrectStudentIsReturned() {
        // Given
        StudentDTO student = new StudentDTO(1, "test@example.com", "Test Student");
        studentService.createStudent(student);

        // When
        Optional<StudentDTO> result = studentService.getStudentByEmail("test@example.com");

        // Then
        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(retrievedStudent -> {
            assertThat(retrievedStudent.getId()).isEqualTo(student.getId());
            assertThat(retrievedStudent.getEmail()).isEqualTo(student.getEmail());
            assertThat(retrievedStudent.getFullName()).isEqualTo(student.getFullName());
        });
    }

    @Test
    void givenNonExistentEmail_whenGettingStudentByEmail_thenEmptyOptionalIsReturned() {
        // When
        Optional<StudentDTO> result = studentService.getStudentByEmail("nonexistent@example.com");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void givenExistingStudent_whenDeletingStudent_thenStudentIsDeletedSuccessfully() {
        // Given
        StudentDTO student = new StudentDTO(1, "test@example.com", "Test Student");
        studentService.createStudent(student);

        // When
        boolean result = studentService.deleteStudent("test@example.com");

        // Then
        assertThat(result).isTrue();
        assertThat(studentService.getStudentByEmail("test@example.com")).isEmpty();
    }

    @Test
    void givenNonExistentEmail_whenDeletingStudent_thenDeletionFails() {
        // When
        boolean result = studentService.deleteStudent("nonexistent@example.com");

        // Then
        assertThat(result).isFalse();
    }
}
