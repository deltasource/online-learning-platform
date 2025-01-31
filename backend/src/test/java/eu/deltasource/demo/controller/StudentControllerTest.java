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
import org.springframework.test.web.servlet.ResultActions;

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

        // When
        ResultActions result = mockMvc.perform(post("/students/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO)));

        // Then
        result.andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(studentDTO)));
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

        // When
        ResultActions result = mockMvc.perform(post("/students/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO)));

        // Then
        result.andExpect(status().isConflict());
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

        // When
        ResultActions result = mockMvc.perform(get("/students/v1/get@example.com"));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(studentDTO)));
    }

    @Test
    public void givenNonExistentStudentEmail_whenGettingStudent_thenReturnNotFound() throws Exception {
        // Given
        when(studentService.getStudentByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When
        ResultActions result = mockMvc.perform(get("/students/v1/nonexistent@example.com"));

        // Then
        result.andExpect(status().isNotFound());
    }

    @Test
    public void givenExistingStudentEmail_whenDeletingStudent_thenReturnNoContent() throws Exception {
        // Given
        when(studentService.deleteStudent("delete@example.com")).thenReturn(true);

        // When
        ResultActions result = mockMvc.perform(delete("/students/v1/delete@example.com"));

        // Then
        result.andExpect(status().isNoContent());
    }

    @Test
    public void givenNonExistentStudentEmail_whenDeletingStudent_thenReturnNotFound() throws Exception {
        // Given
        when(studentService.deleteStudent("nonexistent@example.com")).thenReturn(false);

        // When
        ResultActions result = mockMvc.perform(delete("/students/v1/nonexistent@example.com"));

        // Then
        result.andExpect(status().isNotFound());
    }
}
