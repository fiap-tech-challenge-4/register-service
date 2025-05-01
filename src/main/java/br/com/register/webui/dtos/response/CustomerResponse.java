package br.com.register.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.register.webui.description.Descriptions.CPF_CUSTOMER;
import static br.com.register.webui.description.Descriptions.EMAIL_CUSTOMER;
import static br.com.register.webui.description.Descriptions.ID;
import static br.com.register.webui.description.Descriptions.NAME_CUSTOMER;
import static br.com.register.webui.description.Descriptions.PHONE_CUSTOMER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerResponse {

    @Schema(description = ID)
    private Long id;
    @Schema(description = NAME_CUSTOMER)
    private String name;
    @Schema(description = CPF_CUSTOMER)
    private String cpf;
    @Schema(description = PHONE_CUSTOMER)
    private String phone;
    @Schema(description = EMAIL_CUSTOMER)
    private String email;
}
