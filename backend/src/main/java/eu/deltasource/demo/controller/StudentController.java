package eu.deltasource.demo.controller;

import eu.deltasource.demo.DTOs.StudentDTO;
import eu.deltasource.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentDTO createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @GetMapping("/{email}")
    @ResponseStatus
    public StudentDTO getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email);
    }

    @DeleteMapping("/{email}")
    public boolean deleteStudent(@PathVariable String email) {
        return studentService.deleteStudent(email);
    }
}
