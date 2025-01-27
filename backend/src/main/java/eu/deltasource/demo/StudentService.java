package eu.deltasource.demo;

import eu.deltasource.demo.DTOs.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private static final Map<String, StudentDTO> studentDatabase = new HashMap<>();

    public String createStudent(StudentDTO studentDTO) {
        if (studentDatabase.containsKey(studentDTO.getEmail())) {
            return "Email already registered.";
        }
        studentDatabase.put(studentDTO.getEmail(), studentDTO);
        return "Student created successfully.";
    }

    public StudentDTO getStudentByEmail(String email) {
        return studentDatabase.get(email);
    }

    public String deleteStudent(String email) {
        if (!studentDatabase.containsKey(email)) {
            return "Student not found.";
        }
        studentDatabase.remove(email);
        return "Student deleted successfully.";
    }
}
