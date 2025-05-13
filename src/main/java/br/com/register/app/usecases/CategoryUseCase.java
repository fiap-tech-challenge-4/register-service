package br.com.register.app.usecases;

import br.com.register.app.entities.Category;
import br.com.register.app.exception.BusinessException;
import br.com.register.app.repositories.CategoryRepository;
import br.com.register.utils.Pagination;
import br.com.register.webui.dtos.CategoryDTO;
import br.com.register.webui.dtos.response.CategoryResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.register.errors.Errors.CATEGORY_NOT_EXISTS;
import static br.com.register.webui.converters.CategoryConvert.toCategoryResponse;
import static br.com.register.webui.converters.CategoryConvert.toEntity;
import static br.com.register.webui.converters.CategoryConvert.toResponseList;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryResponse registerCategory(CategoryDTO categoria) {
        var entity = toEntity(categoria);
        var entityResponse = categoryRepository.save(entity);

        return toCategoryResponse(entityResponse);
    }

    public CategoryResponse updateCategory(Long id, CategoryDTO categoryDTO) {
        var entity = getCategory(id);
        entity.setId(id);
        entity.setDescription(categoryDTO.getDescription());

        Category entityResponse = categoryRepository.save(entity);
        return toCategoryResponse(entityResponse);
    }

    public void deleteCategory(Long id) {
        var entity = getCategory(id);
        categoryRepository.delete(entity);
    }

    public CategoryResponse searchCategoryById(Long id) {
        return toCategoryResponse(getCategory(id));
    }

    public PaginationResponse<CategoryResponse> listCategories(Integer page, Integer limit, String sort) {
        var pageable = Pagination.getPageRequest(limit, page, sort, "id");
        var produtos = categoryRepository.findAll(pageable);
        List<CategoryResponse> CategoryResponses = toResponseList(produtos.getContent());
        return new PaginationResponse<>().convertToResponse(new PageImpl(CategoryResponses, produtos.getPageable(), 0L));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CATEGORY_NOT_EXISTS, UNPROCESSABLE_ENTITY));
    }

}
