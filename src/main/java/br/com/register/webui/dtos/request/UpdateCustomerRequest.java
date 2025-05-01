package br.com.register.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.register.errors.Errors.FIELD_REQUIRED;
import static br.com.register.webui.description.Descriptions.EMAIL_CUSTOMER;
import static br.com.register.webui.description.Descriptions.NAME_CUSTOMER;
import static br.com.register.webui.description.Descriptions.PHONE_CUSTOMER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {

    @Schema(description = NAME_CUSTOMER)
    @NotBlank(message = FIELD_REQUIRED)
    private String name;
    @Schema(description = PHONE_CUSTOMER)
    private String phone;
    @Schema(description = EMAIL_CUSTOMER)
    private String email;
}
