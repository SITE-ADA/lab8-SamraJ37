package az.edu.ada.wm2.courseservice.controller;

import az.edu.ada.wm2.courseservice.model.dto.CourseRequestDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseStudentsResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrollmentResponseDto;
import az.edu.ada.wm2.courseservice.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(
        name = "Kurslar",
        description = "Kursların idarə edilməsi, tələbələrin kurslara yazılması və kurs-tələbə əlaqələri üçün endpointlər"
)
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(
            summary = "Yeni kurs yarat",
            description = "Sistemə yeni kurs əlavə edir. Kurs üçün ad, kod, kredit sayı və istəyə bağlı prerequisite kurs id-si göndərilə bilər."
    )
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto requestDto) {
        CourseResponseDto createdCourse = courseService.createCourse(requestDto);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Bütün kursları gətir",
            description = "Sistemdə mövcud olan bütün kursların siyahısını qaytarır."
    )
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/by-student-name")
    @Operation(
            summary = "Tələbə adına görə kursları gətir",
            description = "Verilən ad və ya soyad üzrə uyğun tələbələri tapır və həmin tələbələrin yazıldığı kursları qaytarır."
    )
    public ResponseEntity<List<CourseResponseDto>> getCoursesByStudentName(@RequestParam String name) {
        return ResponseEntity.ok(courseService.getCoursesByStudentName(name));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Kursu id-yə görə gətir",
            description = "Verilən kurs id-si əsasında bir kursun məlumatlarını qaytarır."
    )
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Kurs məlumatlarını yenilə",
            description = "Verilən kurs id-si əsasında mövcud kursun adını, kodunu, kredit sayını və prerequisite kurs id-sini yeniləyir."
    )
    public ResponseEntity<CourseResponseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Kursu sil",
            description = "Verilən kurs id-si əsasında kursu sistemdən silir."
    )
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    @Operation(
            summary = "Tələbəni kursa yaz",
            description = "Tələbəni seçilmiş kursa yazır. Əvvəlcə tələbənin mövcudluğu yoxlanılır, sonra prerequisite tələbi varsa, tələbənin həmin prerequisite kursa yazılıb-yazılmadığı yoxlanılır."
    )
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        EnrollmentResponseDto responseDto = courseService.enrollStudent(courseId, studentId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{courseId}/students")
    @Operation(
            summary = "Kursa yazılmış tələbələri gətir",
            description = "Verilən kurs id-si üzrə həmin kursa yazılmış tələbələrin ətraflı məlumatlarını student-service vasitəsilə qaytarır."
    )
    public ResponseEntity<CourseStudentsResponseDto> getCourseStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseStudents(courseId));
    }
}