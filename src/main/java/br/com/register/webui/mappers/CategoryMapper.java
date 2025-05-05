package br.com.register.webui.mappers;

import br.com.register.app.entities.Category;
import br.com.register.webui.dtos.CategoryDTO;
import br.com.register.webui.dtos.request.RegisterCategoryRequest;
import br.com.register.webui.dtos.request.UpdateCategoryRequest;
import br.com.register.webui.dtos.response.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toCategoryDto(RegisterCategoryRequest request);

    CategoryDTO toCategoryDto(UpdateCategoryRequest request);

    Category toEntity(CategoryDTO categoryDTO);

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);
}
