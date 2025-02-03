package eu.deltasource.demo.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String email) {
        super("Student not found with this email: " + email);
    }
}
