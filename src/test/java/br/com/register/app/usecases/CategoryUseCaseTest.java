package br.com.register.app.usecases;

import br.com.register.app.entities.Category;
import br.com.register.app.exception.BusinessException;
import br.com.register.app.repositories.CategoryRepository;
import br.com.register.webui.dtos.CategoryDTO;
import br.com.register.webui.dtos.response.CategoryResponse;
import br.com.register.webui.mappers.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static br.com.register.errors.Errors.CATEGORY_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryUseCaseTest {

    @InjectMocks
    private CategoryUseCase categoryUseCase;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    private Category category;
    private CategoryDTO categoryDTO;
    private CategoryResponse categoryResponse;

    @BeforeEach
    public void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setDescription("Test Category");

        category = new Category();
        category.setId(1L);
        category.setDescription("Test Category");

        categoryResponse = new CategoryResponse();
        categoryResponse.setId(1L);
        categoryResponse.setDescription("Test Category");
    }

    @Test
    public void testRegisterCategory() {
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        var response = categoryUseCase.registerCategory(categoryDTO);

        assertNotNull(response);
        assertEquals(categoryResponse.getId(), response.getId());
    }

    @Test
    public void testUpdateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        var updatedResponse = categoryUseCase.updateCategory(1L, categoryDTO);

        assertNotNull(updatedResponse);
        assertEquals(categoryDTO.getDescription(), updatedResponse.getDescription());
    }

    @Test
    public void testDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> categoryUseCase.deleteCategory(1L));
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testSearchCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        var result = categoryUseCase.searchCategoryById(1L);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
    }

    @Test
    public void testListCategories() {
        var pageable = PageRequest.of(0, 10, Sort.by("id"));
        var page = new PageImpl<>(List.of(category), pageable, 1L);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryMapper.toResponseList(anyList())).thenReturn(List.of(categoryResponse));

        var result = categoryUseCase.listCategories(0, 10, "asc");

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
    }

    @Test
    public void testGetCategoryThrowsException() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> {
            categoryUseCase.searchCategoryById(99L);
        });

        assertEquals(CATEGORY_NOT_EXISTS, exception.getMessage());
    }

}