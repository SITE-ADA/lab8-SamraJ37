package az.edu.ada.wm2.studentservice.controller;

import az.edu.ada.wm2.studentservice.model.dto.StudentRequestDto;
import az.edu.ada.wm2.studentservice.model.dto.StudentResponseDto;
import az.edu.ada.wm2.studentservice.service.StudentService;
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
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(
        name = "Tələbələr",
        description = "Tələbə məlumatlarının yaradılması, oxunması, yenilənməsi, silinməsi və ada görə axtarışı üçün endpointlər"
)
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(
            summary = "Yeni tələbə yarat",
            description = "Sistemə yeni tələbə məlumatlarını əlavə edir."
    )
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto createdStudent = studentService.createStudent(requestDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Bütün tələbələri gətir",
            description = "Sistemdə mövcud olan bütün tələbələrin siyahısını qaytarır."
    )
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Tələbələri ada görə axtar",
            description = "Verilən ad və ya soyada uyğun tələbələrin siyahısını qaytarır."
    )
    public ResponseEntity<List<StudentResponseDto>> searchStudentsByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudentsByName(name));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Tələbəni id-yə görə gətir",
            description = "Verilən tələbə id-si əsasında bir tələbənin məlumatlarını qaytarır."
    )
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Tələbə məlumatlarını yenilə",
            description = "Verilən tələbə id-si əsasında mövcud tələbənin məlumatlarını yeniləyir."
    )
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Tələbəni sil",
            description = "Verilən tələbə id-si əsasında tələbəni sistemdən silir."
    )
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}