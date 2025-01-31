package eu.deltasource.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.demo.DTOs.StudentDTO;
import eu.deltasource.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenValidStudentData_whenCreatingStudent_thenReturnCreatedStatus() throws Exception {
        // Given
        StudentDTO studentDTO = StudentDTO.builder()
                .id(1)
                .email("test@example.com")
                .fullName("Test Student")
                .build();

        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(Optional.of(studentDTO));

        // When & Then
        mockMvc.perform(post("/students/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.fullName").value("Test Student"));
    }

    @Test
    public void givenExistingEmail_whenCreatingStudent_thenReturnConflict() throws Exception {
        // Given
        StudentDTO studentDTO = StudentDTO.builder()
                .id(1)
                .email("existing@example.com")
                .fullName("Existing Student")
                .build();

        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/students/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void givenExistingStudentEmail_whenGettingStudent_thenReturnStudentDetails() throws Exception {
        // Given
        StudentDTO studentDTO = StudentDTO.builder()
                .id(1)
                .email("get@example.com")
                .fullName("Get Student")
                .build();

        when(studentService.getStudentByEmail("get@example.com")).thenReturn(Optional.of(studentDTO));

        // When & Then
        mockMvc.perform(get("/students/v1/get@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("get@example.com"))
                .andExpect(jsonPath("$.fullName").value("Get Student"));
    }

    @Test
    public void givenNonExistentStudentEmail_whenGettingStudent_thenReturnNotFound() throws Exception {
        // Given
        when(studentService.getStudentByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/students/v1/nonexistent@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistingStudentEmail_whenDeletingStudent_thenReturnNoContent() throws Exception {
        // Given
        when(studentService.deleteStudent("delete@example.com")).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/students/v1/delete@example.com"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenNonExistentStudentEmail_whenDeletingStudent_thenReturnNotFound() throws Exception {
        // Given
        when(studentService.deleteStudent("nonexistent@example.com")).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/students/v1/nonexistent@example.com"))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
