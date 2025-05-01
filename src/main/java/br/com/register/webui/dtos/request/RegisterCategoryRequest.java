package br.com.register.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.register.errors.Errors.FIELD_REQUIRED;
import static br.com.register.webui.description.Descriptions.DESCRIPTION_CATEGORY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCategoryRequest {

    @Schema(description = DESCRIPTION_CATEGORY)
    @NotBlank(message = FIELD_REQUIRED)
    private String description;

}
