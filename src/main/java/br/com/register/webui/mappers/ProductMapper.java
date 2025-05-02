package br.com.register.webui.mappers;

import br.com.register.app.entities.Product;
import br.com.register.webui.dtos.ProductDTO;
import br.com.register.webui.dtos.request.UpdateProductRequest;
import br.com.register.webui.dtos.request.RegisterProductRequest;
import br.com.register.webui.dtos.response.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDto(RegisterProductRequest request);

    ProductDTO toProductDto(UpdateProductRequest request);

    Product toEntity(ProductDTO productDTO);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);
}
