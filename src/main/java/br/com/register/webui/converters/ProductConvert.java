package br.com.register.webui.converters;

import br.com.register.app.entities.Product;
import br.com.register.webui.dtos.ProductDTO;
import br.com.register.webui.dtos.request.RegisterProductRequest;
import br.com.register.webui.dtos.request.UpdateProductRequest;
import br.com.register.webui.dtos.response.CategoryResponse;
import br.com.register.webui.dtos.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConvert {

    public static ProductDTO toProductDto(RegisterProductRequest request) {
        if (request == null)
            return null;

        return ProductDTO.builder()
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice())
                .categoryId(request.getCategoryId())
                .image(request.getImage())
                .build();
    }

    public static ProductDTO toProductDto(UpdateProductRequest request) {
        if (request == null)
            return null;

        return ProductDTO.builder()
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice())
                .categoryId(request.getCategoryId())
                .image(request.getImage())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null)
            return null;

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setDescription(productDTO.getDescription());
        product.setUnitPrice(productDTO.getUnitPrice().doubleValue());
        product.setImage(productDTO.getImage());
        return product;
    }

    public static ProductResponse toProductResponse(Product product) {
        if (product == null)
            return null;

        return ProductResponse.builder()
                .id(product.getId().intValue())
                .description(product.getDescription())
                .unitPrice(BigDecimal.valueOf(product.getUnitPrice()))
                .category(product.getCategory() != null ? CategoryResponse.builder()
                        .id(product.getCategory().getId())
                        .description(product.getCategory().getDescription())
                        .build() : null)
                .image(product.getImage())
                .build();
    }

    public static List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null)
            return null;

        return products.stream()
                .map(ProductConvert::toProductResponse)
                .collect(Collectors.toList());
    }
}
