package br.com.register.webui.controllers;

import br.com.register.app.usecases.ProductUseCase;
import br.com.register.webui.dtos.request.UpdateProductRequest;
import br.com.register.webui.dtos.request.RegisterProductRequest;
import br.com.register.webui.dtos.response.ErrorResponse;
import br.com.register.webui.dtos.response.PaginationProductResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.ProductResponse;
import br.com.register.webui.mappers.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
import static br.com.register.webui.description.Descriptions.CATEGORY_ID;
import static br.com.register.webui.description.Descriptions.ID;
import static br.com.register.webui.description.Descriptions.LIMIT;
import static br.com.register.webui.description.Descriptions.PAGE;
import static br.com.register.webui.description.Descriptions.SORT;
import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Product")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private static final String ASC_DESC = "ASC/DESC";

    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    @Operation(summary = "Register product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})})
    @PostMapping
    public ResponseEntity<ProductResponse> registerProduct(@RequestBody @Valid RegisterProductRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(productUseCase.registerProduct(productMapper.toProductDto(request)));
    }

    @Operation(summary = "Update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final UpdateProductRequest request) {
        var productDTO = productMapper.toProductDto(request);
        return ResponseEntity.ok(productUseCase.updateProduct(id, productDTO));
    }

    @Operation(summary = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete completed successfully",
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed sucessfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> searchProductById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        return ResponseEntity.ok(productUseCase.searchProductById(id));
    }

    @Operation(summary = "List products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search list completed sucessfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationProductResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<ProductResponse>> listProducts(
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
        return ResponseEntity.ok(productUseCase.listProducts(page, limit, sort));
    }

    @Operation(summary = "Search products by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed sucessfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationProductResponse.class))})})
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PaginationResponse<ProductResponse>> searchProductsByCategory(
            @Parameter(description = PAGE)
            @RequestParam(required = false, defaultValue = "1")
            @Min(value = 1, message = PAGE_MIN)
            final Integer page,
            @Parameter(description = LIMIT)
            @RequestParam(required = false, defaultValue = "25")
            final Integer limit,
            @Parameter(description = SORT, example = ASC_DESC)
            @RequestParam(required = false, defaultValue = "DESC")
            final String sort,
            @Parameter(description = CATEGORY_ID)
            @PathVariable(name = "categoryId")
            final Long categoryId) {
        return ResponseEntity.ok(productUseCase.searchProductsByCategory(page, limit, sort, categoryId));
    }

}
