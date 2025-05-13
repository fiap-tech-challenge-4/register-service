package br.com.register.app.usecases;

import br.com.register.app.entities.Category;
import br.com.register.app.entities.Product;
import br.com.register.app.exception.BusinessException;
import br.com.register.app.repositories.CategoryRepository;
import br.com.register.app.repositories.ProductRepository;
import br.com.register.utils.Pagination;
import br.com.register.webui.dtos.ProductDTO;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.register.errors.Errors.CATEGORY_NOT_EXISTS;
import static br.com.register.errors.Errors.PRODUCT_NOT_FOUND;
import static br.com.register.webui.converters.ProductConvert.toEntity;
import static br.com.register.webui.converters.ProductConvert.toProductResponse;
import static br.com.register.webui.converters.ProductConvert.toResponseList;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse registerProduct(ProductDTO productDTO) {
        var category = getCategory(productDTO.getCategoryId());

        var entity = toEntity(productDTO);
        entity.setCategory(category);
        var entityResponse = productRepository.save(entity);

        return toProductResponse(entityResponse);
    }

    public PaginationResponse<ProductResponse> listProducts(Integer page, Integer limit, String sort) {
        var pageable = Pagination.getPageRequest(limit, page, sort, "id");
        var products = productRepository.findAll(pageable);
        List<ProductResponse> listProductResponse = toResponseList(products.getContent());
        return new PaginationResponse<>().convertToResponse(new PageImpl(listProductResponse, products.getPageable(), 0L));
    }

    public ProductResponse updateProduct(Long id, ProductDTO productDTO) {
        getProduct(id);
        var category = getCategory(productDTO.getCategoryId());

        Product entity = toEntity(productDTO);
        entity.setId(id);
        entity.setCategory(category);
        Product entityResponse = productRepository.save(entity);
        return toProductResponse(entityResponse);
    }

    public void deleteProduct(Long id) {
        var product = getProduct(id);
        productRepository.delete(product);
    }

    public ProductResponse searchProductById(Long id) {
        return toProductResponse(getProduct(id));
    }

    public PaginationResponse<ProductResponse> searchProductsByCategory(Integer page, Integer limit, String sort, Long categoriaId) {
        var pageable = Pagination.getPageRequest(limit, page, sort, "id");
        var products = productRepository.findByCategoryId(categoriaId, pageable);
        List<ProductResponse> listProductResponse = toResponseList(products.getContent());
        return new PaginationResponse<>().convertToResponse(new PageImpl(listProductResponse, products.getPageable(), 0L));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND, UNPROCESSABLE_ENTITY));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CATEGORY_NOT_EXISTS, UNPROCESSABLE_ENTITY));
    }

}
