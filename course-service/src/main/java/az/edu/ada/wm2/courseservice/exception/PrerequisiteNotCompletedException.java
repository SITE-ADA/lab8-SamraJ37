package az.edu.ada.wm2.courseservice.exception;

public class PrerequisiteNotCompletedException extends RuntimeException {

    public PrerequisiteNotCompletedException(Long courseId, Long prerequisiteCourseId, Long studentId) {
        super("Student with id " + studentId
                + " cannot enroll in course " + courseId
                + " because prerequisite course " + prerequisiteCourseId
                + " has not been completed.");
    }
}