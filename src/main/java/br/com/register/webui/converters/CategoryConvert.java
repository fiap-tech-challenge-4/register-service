package br.com.register.webui.converters;

import br.com.register.app.entities.Category;
import br.com.register.webui.dtos.CategoryDTO;
import br.com.register.webui.dtos.request.RegisterCategoryRequest;
import br.com.register.webui.dtos.request.UpdateCategoryRequest;
import br.com.register.webui.dtos.response.CategoryResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConvert {

    public static CategoryDTO toCategoryDto(RegisterCategoryRequest request) {
        if (request == null)
            return null;

        return CategoryDTO.builder()
                .description(request.getDescription())
                .build();
    }

    public static CategoryDTO toCategoryDto(UpdateCategoryRequest request) {
        if (request == null)
            return null;

        return CategoryDTO.builder()
                .description(request.getDescription())
                .build();
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null)
            return null;

        return Category.builder()
                .description(categoryDTO.getDescription())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        if (category == null)
            return null;
        return CategoryResponse.builder()
                .id(category.getId())
                .description(category.getDescription())
                .build();
    }

    public static List<CategoryResponse> toResponseList(List<Category> categories) {
        if (categories == null)
            return null;
        return categories.stream()
                .map(CategoryConvert::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
