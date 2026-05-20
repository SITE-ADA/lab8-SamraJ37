package az.edu.ada.wm2.studentservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {

    @Schema(description = "Tələbənin adı", example = "Nicat")
    @NotBlank(message = "Tələbənin adı tələb olunur")
    private String firstName;

    @Schema(description = "Tələbənin soyadı", example = "Aliyev")
    @NotBlank(message = "Tələbənin soyadı tələb olunur")
    private String lastName;

    @Schema(description = "Tələbənin email ünvanı", example = "nicat.aliyev@example.com")
    @NotBlank(message = "Email ünvanı tələb olunur")
    @Email(message = "Email formatı düzgün deyil")
    private String email;

    @Schema(description = "Tələbənin yaşı", example = "20", minimum = "16")
    @Min(value = 16, message = "Yaş ən azı 16 olmalıdır")
    private Integer age;
}