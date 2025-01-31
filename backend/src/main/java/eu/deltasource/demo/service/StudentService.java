package eu.deltasource.demo.service;

import eu.deltasource.demo.DTOs.StudentDTO;
import eu.deltasource.demo.model.Student;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final Map<String, Student> studentDatabase = new HashMap<>();

    public Optional<StudentDTO> createStudent(StudentDTO studentDTO) {
        if (studentDatabase.containsKey(studentDTO.getEmail())) {
            return Optional.empty();
        }
        Student student = new Student(studentDTO.getId(), studentDTO.getEmail(), studentDTO.getFullName());
        studentDatabase.put(student.getEmail(), student);
        return Optional.of(studentDTO);
    }

    public Optional<StudentDTO> getStudentByEmail(String email) {
        Student student = studentDatabase.get(email);
        if (student == null) {
            return Optional.empty();
        }
        return Optional.of(new StudentDTO(student.getId(), student.getEmail(), student.getFullName()));
    }

    public boolean deleteStudent(String email) {
        return studentDatabase.remove(email) != null;
    }
}
