package br.com.register.webui.controllers;

import br.com.register.app.usecases.CustomerUseCase;
import br.com.register.webui.dtos.request.UpdateCustomerRequest;
import br.com.register.webui.dtos.request.RegisterCustomerRequest;
import br.com.register.webui.dtos.response.CustomerResponse;
import br.com.register.webui.dtos.response.ErrorResponse;
import br.com.register.webui.dtos.response.PaginationCustomerResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.mappers.CustomerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.register.errors.Errors.PAGE_MIN;
import static br.com.register.errors.Errors.QUERY_PARAMS_REQUERIDO;
import static br.com.register.webui.description.Descriptions.CPF_CUSTOMER;
import static br.com.register.webui.description.Descriptions.ID;
import static br.com.register.webui.description.Descriptions.LIMIT;
import static br.com.register.webui.description.Descriptions.PAGE;
import static br.com.register.webui.description.Descriptions.SORT;
import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Customer")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    private static final String ASC_DESC = "ASC/DESC";

    private final CustomerUseCase customerUseCase;
    private final CustomerMapper customerMapper;

    @Operation(summary = "Register customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))})})
    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody @Valid RegisterCustomerRequest request) {
        var response = customerUseCase.registerCustomer(customerMapper.toCustomerDto(request));
        return ResponseEntity.status(CREATED).body(response);
    }

    @Operation(summary = "Update customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final UpdateCustomerRequest request) {
        var customerDTO = customerMapper.toCustomerDto(request);
        return ResponseEntity.ok(customerUseCase.updateCustomer(id, customerDTO));
    }

    @Operation(summary = "Delete customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete completed successfully",
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaProduto(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        customerUseCase.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> searchCustomerById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        return ResponseEntity.ok(customerUseCase.searchCustomerById(id));
    }

    @Operation(summary = "Search customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))})})
    @GetMapping
    public ResponseEntity<CustomerResponse> searchCustomer(
            @Parameter(description = CPF_CUSTOMER)
            @RequestParam(required = false)
            @NotBlank(message = QUERY_PARAMS_REQUERIDO)
            final String cpf) {
        return ResponseEntity.ok(customerUseCase.searchCustomer(cpf));
    }

    @Operation(summary = "List customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search list completed sucessfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationCustomerResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<CustomerResponse>> listCustomers(
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
        return ResponseEntity.ok(customerUseCase.listCustomers(page, limit, sort));
    }
}
