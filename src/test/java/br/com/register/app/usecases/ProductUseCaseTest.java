package br.com.register.app.usecases;

import br.com.register.app.entities.Category;
import br.com.register.app.entities.Product;
import br.com.register.app.exception.BusinessException;
import br.com.register.app.repositories.CategoryRepository;
import br.com.register.app.repositories.ProductRepository;
import br.com.register.webui.dtos.ProductDTO;
import br.com.register.webui.dtos.response.CategoryResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.ProductResponse;
import br.com.register.webui.mappers.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static br.com.register.errors.Errors.CATEGORY_NOT_EXISTS;
import static br.com.register.errors.Errors.PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductUseCaseTest {

    @InjectMocks
    private ProductUseCase productUseCase;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryRepository categoryRepository;

    private ProductDTO productDTO;
    private Product product;
    private ProductResponse productResponse;
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setDescription("Category 1");

        productDTO = new ProductDTO();
        productDTO.setDescription("Product 1");
        productDTO.setUnitPrice(BigDecimal.valueOf(99.99));
        productDTO.setCategoryId(1L);
        productDTO.setImage("image.png");

        product = new Product();
        product.setId(1L);
        product.setDescription("Product 1");
        product.setUnitPrice(99.99);
        product.setCategory(category);
        product.setImage("image.png");

        productResponse = new ProductResponse();
        productResponse.setId(1);
        productResponse.setDescription("Product 1");
        productResponse.setUnitPrice(BigDecimal.valueOf(99.99));
        productResponse.setImage("image.png");
        productResponse.setCategory(new CategoryResponse());
    }

    @Test
    public void testRegisterProductSuccess() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductResponse(product)).thenReturn(productResponse);

        ProductResponse result = productUseCase.registerProduct(productDTO);

        assertNotNull(result);
        assertEquals(productResponse.getDescription(), result.getDescription());
    }

    @Test
    public void testRegisterProductCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            productUseCase.registerProduct(productDTO);
        });
        assertEquals(CATEGORY_NOT_EXISTS, ex.getMessage());
    }

    @Test
    public void testListProducts() {
        var pageable = PageRequest.of(0, 10, Sort.by("id"));
        var page = new PageImpl<>(List.of(product), pageable, 1L);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(productMapper.toResponseList(anyList())).thenReturn(List.of(productResponse));

        PaginationResponse<ProductResponse> response = productUseCase.listProducts(0, 10, "asc");

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
    }

    @Test
    public void testUpdateProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductResponse(product)).thenReturn(productResponse);

        ProductResponse response = productUseCase.updateProduct(1L, productDTO);

        assertNotNull(response);
        assertEquals("Product 1", response.getDescription());
    }

    @Test
    public void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            productUseCase.updateProduct(1L, productDTO);
        });

        assertEquals(PRODUCT_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void testUpdateProductCategoryNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            productUseCase.updateProduct(1L, productDTO);
        });

        assertEquals(CATEGORY_NOT_EXISTS, ex.getMessage());
    }

    @Test
    public void testDeleteProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productUseCase.deleteProduct(1L));
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            productUseCase.deleteProduct(1L);
        });

        assertEquals(PRODUCT_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void testSearchProductByIdSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponse(product)).thenReturn(productResponse);

        ProductResponse response = productUseCase.searchProductById(1L);

        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    public void testSearchProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            productUseCase.searchProductById(1L);
        });

        assertEquals(PRODUCT_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void testSearchProductsByCategory() {
        var pageable = PageRequest.of(0, 10, Sort.by("id"));
        var page = new PageImpl<>(List.of(product), pageable, 1L);

        when(productRepository.findByCategoryId(1L, pageable)).thenReturn(page);
        when(productMapper.toResponseList(anyList())).thenReturn(List.of(productResponse));

        PaginationResponse<ProductResponse> response = productUseCase.searchProductsByCategory(0, 10, "asc", 1L);

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
    }
}