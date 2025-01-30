package eu.deltasource.demo.controller;

import eu.deltasource.demo.DTOs.StudentDTO;
import eu.deltasource.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    public void givenValidStudentData_whenCreatingStudent_thenReturnCreatedStatus() throws Exception {
        // Given
        String studentJson = "{\"email\":\"test@example.com\",\"fullName\":\"Test Student\"}";
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn("Student created successfully.");

        // When
        mockMvc.perform(post("/students/v1").contentType(MediaType.APPLICATION_JSON).content(studentJson))

                // Then
                .andExpect(status().isCreated()).andExpect(content().string("Student created successfully."));
    }

    @Test
    public void givenExistingEmail_whenCreatingStudent_thenReturnBadRequest() throws Exception {
        // Given
        String studentJson = "{\"email\":\"existing@example.com\",\"fullName\":\"Existing Student\"}";
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn("Student with this email already exists.");

        // When
        mockMvc.perform(post("/students/v1").contentType(MediaType.APPLICATION_JSON).content(studentJson))
                // Then
                .andExpect(status().isBadRequest()).andExpect(content().string("Student with this email already exists."));
    }

    @Test
    public void givenExistingStudentEmail_whenGettingStudent_thenReturnStudentDetails() throws Exception {
        // Given
        StudentDTO studentDTO = new StudentDTO(1, "get@example.com", "Get Student");
        when(studentService.getStudentByEmail("get@example.com")).thenReturn(studentDTO);

        // When
        mockMvc.perform(get("/students/v1/get@example.com"))
                // Then
                .andExpect(status().isOk()).andExpect(jsonPath("$.email").value("get@example.com")).andExpect(jsonPath("$.fullName").value("Get Student"));
    }

    @Test
    public void givenNonExistentStudentEmail_whenGettingStudent_thenReturnNotFound() throws Exception {
        // Given
        when(studentService.getStudentByEmail("nonexistent@example.com")).thenReturn(null);

        // When
        mockMvc.perform(get("/students/v1/nonexistent@example.com"))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistingStudentEmail_whenDeletingStudent_thenReturnSuccessMessage() throws Exception {
        // Given
        when(studentService.deleteStudent("delete@example.com")).thenReturn("Student deleted successfully.");

        // When
        mockMvc.perform(delete("/students/v1/delete@example.com"))
                // Then
                .andExpect(status().isOk()).andExpect(content().string("Student deleted successfully."));
    }

    @Test
    public void givenNonExistentStudentEmail_whenDeletingStudent_thenReturnNotFound() throws Exception {
        // Given
        when(studentService.deleteStudent("nonexistent@example.com")).thenReturn("Student not found.");

        // When
        mockMvc.perform(delete("/students/v1/nonexistent@example.com"))
                // Then
                .andExpect(status().isNotFound()).andExpect(content().string("Student not found."));
    }
}
