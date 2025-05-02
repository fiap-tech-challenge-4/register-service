package br.com.register.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.register.errors.Errors.FIELD_REQUIRED;
import static br.com.register.errors.Errors.UNIT_AMOUNT_FORMAT_INVALID;
import static br.com.register.webui.description.Descriptions.CATEGORY_ID;
import static br.com.register.webui.description.Descriptions.DESCRIPTION_PRODUCT;
import static br.com.register.webui.description.Descriptions.IMAGE_PRODUCT;
import static br.com.register.webui.description.Descriptions.UNIT_PRICE_PRODUCT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    @Schema(description = DESCRIPTION_PRODUCT)
    @NotBlank(message = FIELD_REQUIRED)
    private String description;
    @Schema(description = UNIT_PRICE_PRODUCT)
    @NotNull(message = FIELD_REQUIRED)
    @Digits(integer = 10, fraction = 2, message = UNIT_AMOUNT_FORMAT_INVALID)
    private BigDecimal unitPrice;
    @Schema(description = CATEGORY_ID)
    @NotNull(message = FIELD_REQUIRED)
    private Long categoryId;
    @Schema(description = IMAGE_PRODUCT)
    @NotBlank(message = FIELD_REQUIRED)
    private String image;

}
