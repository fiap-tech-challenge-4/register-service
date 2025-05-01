package br.com.register.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static br.com.register.webui.description.Descriptions.FIELDS;
import static br.com.register.webui.description.Descriptions.HTTP_CODE;
import static br.com.register.webui.description.Descriptions.HTTP_DESCRIPTION;
import static br.com.register.webui.description.Descriptions.MESSAGE;
import static br.com.register.webui.description.Descriptions.PATH;
import static br.com.register.webui.description.Descriptions.TIMESTAMP;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @Schema(description = HTTP_CODE)
    private int httpCode;
    @Schema(description = HTTP_DESCRIPTION)
    private String httpDescription;
    @Schema(description = MESSAGE)
    private String message;
    @Schema(description = PATH)
    private String path;
    @Schema(description = TIMESTAMP)
    private String timestamp;
    @Schema(description = FIELDS)
    private List<ErrorField> fields;
}
