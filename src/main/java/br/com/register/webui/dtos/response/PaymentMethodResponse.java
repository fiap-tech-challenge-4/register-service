package br.com.register.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.register.webui.description.Descriptions.DESCRIPTION_PAYMENT_METHOD;
import static br.com.register.webui.description.Descriptions.ID;
import static br.com.register.webui.description.Descriptions.PAYMENT_TYPE_PAYMENT_METHOD;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PaymentMethodResponse {

    @Schema(description = ID)
    private Long id;
    @Schema(description = DESCRIPTION_PAYMENT_METHOD)
    private String description;
    @Schema(description = PAYMENT_TYPE_PAYMENT_METHOD)
    private String paymentType;

}
