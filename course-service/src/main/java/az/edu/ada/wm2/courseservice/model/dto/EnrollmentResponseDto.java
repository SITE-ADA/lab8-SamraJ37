package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDto {

    @Schema(description = "Yazılma id-si", example = "10")
    private Long enrollmentId;

    @Schema(description = "Kurs id-si", example = "1")
    private Long courseId;

    @Schema(description = "Tələbə id-si", example = "15")
    private Long studentId;

    @Schema(description = "Tələbənin kursa yazılma tarixi", example = "2026-05-20")
    private LocalDate enrollmentDate;

    @Schema(description = "Əməliyyat nəticəsi barədə mesaj", example = "Tələbə kursa uğurla yazıldı.")
    private String message;
}