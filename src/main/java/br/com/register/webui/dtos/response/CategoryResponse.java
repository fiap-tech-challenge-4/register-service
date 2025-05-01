package br.com.register.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.register.webui.description.Descriptions.DESCRIPTION_CATEGORY;
import static br.com.register.webui.description.Descriptions.ID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CategoryResponse {

    @Schema(description = ID)
    private Long id;
    @Schema(description = DESCRIPTION_CATEGORY)
    private String description;
}
