package br.com.register.webui.controllers;

import br.com.register.app.usecases.PaymentMethodUseCase;
import br.com.register.webui.dtos.response.ErrorResponse;
import br.com.register.webui.dtos.response.PaginationPaymentMethodResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.PaymentMethodResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.register.errors.Errors.PAGE_MIN;
import static br.com.register.webui.description.Descriptions.LIMIT;
import static br.com.register.webui.description.Descriptions.PAGE;
import static br.com.register.webui.description.Descriptions.SORT;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Payment method")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/payment-method")
public class PaymentMethodController {

    private static final String ASC_DESC = "ASC/DESC";

    private final PaymentMethodUseCase service;

    @Operation(summary = "List payment method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search list completed sucessfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationPaymentMethodResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<PaymentMethodResponse>> listPaymentMethod(
            @Parameter(description = PAGE)
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1, message = PAGE_MIN)
            final Integer page,
            @Parameter(description = LIMIT)
            @RequestParam(required = false, defaultValue = "25")
            final Integer limit,
            @Parameter(description = SORT, example = ASC_DESC)
            @RequestParam(required = false, defaultValue = "DESC")
            final String sort) {
        return ResponseEntity.ok(service.listPaymentMethod(page, limit, sort));
    }
}
