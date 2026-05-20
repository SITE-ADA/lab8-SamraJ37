package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @Schema(description = "Kursun adı", example = "Data Structures")
    @NotBlank(message = "Kursun adı tələb olunur")
    private String title;

    @Schema(description = "Kursun kodu", example = "CS201")
    @NotBlank(message = "Kursun kodu tələb olunur")
    private String code;

    @Schema(description = "Kursun kredit sayı", example = "4")
    @Positive(message = "Kredit sayı müsbət olmalıdır")
    private Integer credits;

    @Schema(
            description = "Bu kurs üçün prerequisite kurs id-si. Əgər prerequisite yoxdursa, null göndərilir.",
            example = "1",
            nullable = true
    )
    private Long prerequisiteCourseId;
}