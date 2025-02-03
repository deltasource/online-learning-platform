package eu.deltasource.demo.service;

import eu.deltasource.demo.DTOs.StudentDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StudentServiceTest {

    private final StudentService studentService = new StudentService();

    @Test
    void givenNewStudent_whenCreatingStudent_thenStudentIsCreatedSuccessfully() {
        // Given
        StudentDTO newStudent = new StudentDTO(1, "test@test.com", "Test Student");

        // When
        StudentDTO result = studentService.createStudent(newStudent);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(newStudent.getId());
        assertThat(result.getEmail()).isEqualTo(newStudent.getEmail());
        assertThat(result.getFullName()).isEqualTo(newStudent.getFullName());
    }

    @Test
    void givenExistingStudent_whenGettingStudentByEmail_thenCorrectStudentIsReturned() {
        // Given
        StudentDTO student = new StudentDTO(1, "test@example.com", "Test Student");
        studentService.createStudent(student);

        // When
        StudentDTO result = studentService.getStudentByEmail("test@example.com");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(student.getId());
        assertThat(result.getEmail()).isEqualTo(student.getEmail());
        assertThat(result.getFullName()).isEqualTo(student.getFullName());
    }


    @Test
    void givenNonExistentEmail_whenDeletingStudent_thenDeletionFails() {
        // When
        boolean result = studentService.deleteStudent("nonexistent@example.com");

        // Then
        assertThat(result).isFalse();
    }
}