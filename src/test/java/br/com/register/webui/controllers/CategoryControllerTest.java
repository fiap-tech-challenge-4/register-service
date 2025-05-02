package br.com.register.webui.controllers;

import br.com.register.app.usecases.CategoryUseCase;
import br.com.register.errors.Errors;
import br.com.register.webui.dtos.CategoryDTO;
import br.com.register.webui.dtos.request.RegisterCategoryRequest;
import br.com.register.webui.dtos.request.UpdateCategoryRequest;
import br.com.register.webui.dtos.response.CategoryResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.mappers.CategoryMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static br.com.register.errors.Errors.FIELD_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryUseCase categoryUseCase;
    @Mock
    private CategoryMapper categoryMapper;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldSuccessfully_registerCategory() {
        RegisterCategoryRequest request = new RegisterCategoryRequest();
        request.setDescription("Categoria Teste");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setDescription("Categoria Teste");

        CategoryResponse expectedResponse = new CategoryResponse();
        expectedResponse.setId(1L);
        expectedResponse.setDescription("Categoria Teste");

        when(categoryMapper.toCategoryDto(request)).thenReturn(categoryDTO);
        when(categoryUseCase.registerCategory(categoryDTO)).thenReturn(expectedResponse);

        ResponseEntity<CategoryResponse> response = categoryController.registerCategory(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse.getId(), response.getBody().getId());
        assertEquals(expectedResponse.getDescription(), response.getBody().getDescription());

        verify(categoryMapper).toCategoryDto(request);
        verify(categoryUseCase).registerCategory(categoryDTO);
    }

    @Test
    public void shouldPassValidationWhenDescriptionIsPresent_registerCategory() {
        RegisterCategoryRequest request = new RegisterCategoryRequest();
        request.setDescription("Categoria válida");

        Set<ConstraintViolation<RegisterCategoryRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldFailValidationWhenDescriptionIsBlank_registerCategory() {
        RegisterCategoryRequest request = new RegisterCategoryRequest();
        request.setDescription("  "); // string em branco

        Set<ConstraintViolation<RegisterCategoryRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterCategoryRequest> violation = violations.iterator().next();
        assertEquals(FIELD_REQUIRED, violation.getMessage());
    }

    @Test
    public void shouldFailValidationWhenDescriptionIsNull_registerCategory() {
        RegisterCategoryRequest request = new RegisterCategoryRequest();
        request.setDescription(null);

        Set<ConstraintViolation<RegisterCategoryRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterCategoryRequest> violation = violations.iterator().next();
        assertEquals(FIELD_REQUIRED, violation.getMessage());
    }

    @Test
    public void shouldSuccessfully_updateCategory() {
        Long id = 1L;
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setDescription("Nova descrição");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Nova descrição");

        CategoryResponse expectedResponse = new CategoryResponse();
        expectedResponse.setId(1L);
        expectedResponse.setDescription("Nova descrição");

        when(categoryMapper.toCategoryDto(request)).thenReturn(categoryDTO);
        when(categoryUseCase.updateCategory(id, categoryDTO)).thenReturn(expectedResponse);

        ResponseEntity<CategoryResponse> response = categoryController.updateCategory(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getId(), response.getBody().getId());
        assertEquals(expectedResponse.getDescription(), response.getBody().getDescription());

        verify(categoryMapper).toCategoryDto(request);
        verify(categoryUseCase).updateCategory(id, categoryDTO);
    }

    @Test
    public void shouldPassValidationWhenDescriptionIsPresent_updateCategory() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setDescription("Descrição válida");

        Set<ConstraintViolation<UpdateCategoryRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldFailValidationWhenDescriptionIsBlank_updateCategory() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setDescription("   ");

        Set<ConstraintViolation<UpdateCategoryRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<UpdateCategoryRequest> violation = violations.iterator().next();
        assertEquals(FIELD_REQUIRED, violation.getMessage()); // ou FIELD_REQUIRED se for constante
    }

    @Test
    public void shouldFailValidationWhenDescriptionIsNull_updateCategory() {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setDescription(null);

        Set<ConstraintViolation<UpdateCategoryRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<UpdateCategoryRequest> violation = violations.iterator().next();
        assertEquals(FIELD_REQUIRED, violation.getMessage());
    }

    @Test
    public void shouldSuccessfully_deleteCategory() {
        Long categoryId = 1L;

        doNothing().when(categoryUseCase).deleteCategory(categoryId);

        ResponseEntity<Void> response = categoryController.deleteCategory(categoryId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(categoryUseCase).deleteCategory(categoryId);
        verifyNoMoreInteractions(categoryUseCase);
    }

    @Test
    public void shouldSuccessfully_searchCategoryById() {
        Long categoryId = 1L;
        CategoryResponse expectedResponse = new CategoryResponse();
        expectedResponse.setId(categoryId);
        expectedResponse.setDescription("Categoria encontrada");

        when(categoryUseCase.searchCategoryById(categoryId)).thenReturn(expectedResponse);

        ResponseEntity<CategoryResponse> response = categoryController.searchCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getId(), response.getBody().getId());
        assertEquals(expectedResponse.getDescription(), response.getBody().getDescription());

        verify(categoryUseCase).searchCategoryById(categoryId);
        verifyNoMoreInteractions(categoryUseCase);
    }

    @Test
    void shouldSuccessfully_listCategories() {
        Integer page = 1;
        Integer limit = 25;
        String sort = "DESC";

        CategoryResponse categoryResponse1 = new CategoryResponse();
        categoryResponse1.setId(1L);
        categoryResponse1.setDescription("Categoria 1");

        CategoryResponse categoryResponse2 = new CategoryResponse();
        categoryResponse2.setId(2L);
        categoryResponse2.setDescription("Categoria 2");

        List<CategoryResponse> categoryResponses = List.of(categoryResponse1, categoryResponse2);

        Page<CategoryResponse> pageResponse = new PageImpl<>(categoryResponses, PageRequest.of(page - 1, limit), categoryResponses.size());

        PaginationResponse<CategoryResponse> paginationResponse = new PaginationResponse<>();
        paginationResponse.convertToResponse(pageResponse);

        when(categoryUseCase.listCategories(page, limit, sort)).thenReturn(paginationResponse);

        ResponseEntity<PaginationResponse<CategoryResponse>> response = categoryController.listCategories(page, limit, sort);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paginationResponse.getItems().size(), response.getBody().getItems().size());
        assertEquals("Categoria 1", response.getBody().getItems().get(0).getDescription());
        assertEquals("Categoria 2", response.getBody().getItems().get(1).getDescription());

        verify(categoryUseCase).listCategories(page, limit, sort);
        verifyNoMoreInteractions(categoryUseCase);
    }
}