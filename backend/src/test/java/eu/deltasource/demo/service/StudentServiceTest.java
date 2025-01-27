package eu.deltasource.demo.service;

import eu.deltasource.demo.DTOs.StudentDTO;
import eu.deltasource.demo.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp(){
        studentService = new StudentService();
    }

    @Test
    void givenNewStudent_whenCreatingStudent_thenStudentIsCreatedSuccessfully() {
        // Given
        StudentDTO newStudent = new StudentDTO(1,"test@test.com", "Test");

        // When
        String result = studentService.createStudent(newStudent);

        // Then
        assertEquals("Student created successfully.", result);
        assertEquals(newStudent, studentService.getStudentByEmail("test@test.com"));
    }

    @Test
    void givenExistingStudentEmail_whenCreatingStudent_thenCreationFails() {
        // Given
        StudentDTO existingStudent = new StudentDTO(1,"test@test.com", "test");
        studentService.createStudent(existingStudent);

        // When
        String result = studentService.createStudent(existingStudent);

        // Then
        assertEquals("Email already registered.", result);
    }

    @Test
    void givenExistingStudent_whenGettingStudentByEmail_thenCorrectStudentIsReturned() {
        // Given
        StudentDTO student = new StudentDTO(1, "test@example.com", "test test");
        studentService.createStudent(student);

        // When
        StudentDTO result = studentService.getStudentByEmail("test@example.com");

        // Then
        assertEquals(student, result);
    }

    @Test
    void givenNonExistentEmail_whenGettingStudentByEmail_thenNullIsReturned() {
        // When
        StudentDTO result = studentService.getStudentByEmail("nonexistent@example.com");

        // Then
        assertNull(result);
    }

    @Test
    void givenExistingStudent_whenDeletingStudent_thenStudentIsDeletedSuccessfully() {
        // Given
        StudentDTO student = new StudentDTO(1,"test@example.com", "test test");
        studentService.createStudent(student);

        // When
        String result = studentService.deleteStudent("test@example.com");

        // Then
        assertEquals("Student deleted successfully.", result);
        assertNull(studentService.getStudentByEmail("test@example.com"));
    }

    @Test
    void givenNonExistentEmail_whenDeletingStudent_thenDeletionFails() {
        // When
        String result = studentService.deleteStudent("nonexistent@example.com");

        // Then
        assertEquals("Student not found.", result);
    }
}
