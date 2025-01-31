package eu.deltasource.demo.controller;

import eu.deltasource.demo.DTOs.StudentDTO;
import eu.deltasource.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Optional<StudentDTO> createdStudent = studentService.createStudent(studentDTO);
        return createdStudent
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/{email}")
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String email) {
        Optional<StudentDTO> student = studentService.getStudentByEmail(email);
        return student
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String email) {
        boolean deleted = studentService.deleteStudent(email);
        return deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
