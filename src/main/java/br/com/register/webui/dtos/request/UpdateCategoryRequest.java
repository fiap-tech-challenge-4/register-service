package br.com.register.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.register.errors.Errors.CAMPO_REQUERIDO;
import static br.com.register.webui.description.Descriptions.DESCRIPTION_CATEGORY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {

    @Schema(description = DESCRIPTION_CATEGORY)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String description;

}
