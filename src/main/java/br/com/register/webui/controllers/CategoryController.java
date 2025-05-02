package br.com.register.webui.controllers;

import br.com.register.app.usecases.CategoryUseCase;
import br.com.register.webui.dtos.request.UpdateCategoryRequest;
import br.com.register.webui.dtos.request.RegisterCategoryRequest;
import br.com.register.webui.dtos.response.CategoryResponse;
import br.com.register.webui.dtos.response.ErrorResponse;
import br.com.register.webui.dtos.response.PaginationCategoryResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.mappers.CategoryMapper;
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
import static br.com.register.webui.description.Descriptions.ID;
import static br.com.register.webui.description.Descriptions.LIMIT;
import static br.com.register.webui.description.Descriptions.PAGE;
import static br.com.register.webui.description.Descriptions.SORT;
import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Category")
@ApiResponse(responseCode = "400", description = "Bad Request",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/category")
public class CategoryController {

    private static final String ASC_DESC = "ASC/DESC";

    private final CategoryUseCase categoryUseCase;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Register category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registration completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})})
    @PostMapping
    public ResponseEntity<CategoryResponse> registerCategory(@RequestBody @Valid RegisterCategoryRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(categoryUseCase.registerCategory(categoryMapper.toCategoryDto(request)));
    }

    @Operation(summary = "Update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id,
            @RequestBody @Valid
            final UpdateCategoryRequest request) {
        var categoryDTO = categoryMapper.toCategoryDto(request);
        return ResponseEntity.ok(categoryUseCase.updateCategory(id, categoryDTO));
    }

    @Operation(summary = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted completed successfully",
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        categoryUseCase.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> searchCategoryById(
            @Parameter(description = ID)
            @PathVariable(name = "id")
            final Long id) {
        return ResponseEntity.ok(categoryUseCase.searchCategoryById(id));
    }

    @Operation(summary = "List categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search list completed sucessfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationCategoryResponse.class))})})
    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<CategoryResponse>> listCategories(
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
        return ResponseEntity.ok(categoryUseCase.listCategories(page, limit, sort));
    }

}
