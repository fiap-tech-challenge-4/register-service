package br.com.register.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.register.webui.description.Descriptions.CATEGORY_PRODUCT;
import static br.com.register.webui.description.Descriptions.DESCRIPTION_PRODUCT;
import static br.com.register.webui.description.Descriptions.ID;
import static br.com.register.webui.description.Descriptions.IMAGE_PRODUCT;
import static br.com.register.webui.description.Descriptions.UNIT_PRICE_PRODUCT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductResponse {

    @Schema(description = ID)
    private Integer id;
    @Schema(description = DESCRIPTION_PRODUCT)
    private String description;
    @Schema(description = UNIT_PRICE_PRODUCT)
    private BigDecimal unitPrice;
    @Schema(description = CATEGORY_PRODUCT)
    private CategoryResponse category;
    @Schema(description = IMAGE_PRODUCT)
    private String image;
}
