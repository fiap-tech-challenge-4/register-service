package br.com.register.webui.controllers;

import br.com.register.app.usecases.ProductUseCase;
import br.com.register.webui.dtos.ProductDTO;
import br.com.register.webui.dtos.request.RegisterProductRequest;
import br.com.register.webui.dtos.request.UpdateProductRequest;
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
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductUseCase productUseCase;
    @Mock
    private ProductMapper productMapper;

    private ProductDTO productDTO;
    private ProductResponse productResponse;
    private CategoryResponse categoryResponse;

    @BeforeEach
    public void setup() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setDescription("Produto Teste");
        productDTO.setUnitPrice(new BigDecimal("9.99"));
        productDTO.setCategoryId(1L);
        productDTO.setImage("img.png");

        categoryResponse = new CategoryResponse();
        categoryResponse.setId(1L);
        categoryResponse.setDescription("Categoria");

        productResponse = new ProductResponse();
        productResponse.setId(1);
        productResponse.setDescription("Produto Teste");
        productResponse.setUnitPrice(new BigDecimal("9.99"));
        productResponse.setImage("img.png");
        productResponse.setCategory(categoryResponse);
    }

    @Test
    public void shouldSuccessfully_registerProduct() {
        RegisterProductRequest request = new RegisterProductRequest();
        request.setDescription("Produto Teste");
        request.setUnitPrice(new BigDecimal("9.99"));
        request.setCategoryId(1L);
        request.setImage("img.png");

        when(productMapper.toProductDto(request)).thenReturn(productDTO);
        when(productUseCase.registerProduct(productDTO)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.registerProduct(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(productResponse, response.getBody());
    }

    @Test
    public void shouldSuccessfully_updateProduct() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setDescription("Produto Atualizado");
        request.setUnitPrice(new BigDecimal("19.99"));
        request.setCategoryId(2L);
        request.setImage("img_updated.png");

        ProductDTO updatedDto = new ProductDTO();
        updatedDto.setDescription("Produto Atualizado");
        updatedDto.setUnitPrice(new BigDecimal("19.99"));
        updatedDto.setCategoryId(2L);
        updatedDto.setImage("img_updated.png");

        when(productMapper.toProductDto(request)).thenReturn(updatedDto);
        when(productUseCase.updateProduct(1L, updatedDto)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.updateProduct(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponse, response.getBody());
    }

    @Test
    public void shouldSuccessfully_deleteProduct() {
        doNothing().when(productUseCase).deleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(productUseCase, times(1)).deleteProduct(1L);
    }

    @Test
    public void shouldSuccessfully_searchProductById() {
        when(productUseCase.searchProductById(1L)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.searchProductById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponse, response.getBody());
    }

    @Test
    public void shouldSuccessfully_listProducts() {
        PaginationResponse<ProductResponse> pagination = new PaginationResponse<>();
        pagination.setItems(List.of(productResponse));
        pagination.setPageNumber(1);
        pagination.setPageSize(25);
        pagination.setHasNext(false);
        pagination.setHasPrevious(false);

        when(productUseCase.listProducts(1, 25, "DESC")).thenReturn(pagination);

        ResponseEntity<PaginationResponse<ProductResponse>> response = productController.listProducts(1, 25, "DESC");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getItems().size());
    }

    @Test
    public void shouldSuccessfully_searchProductsByCategory() {
        PaginationResponse<ProductResponse> pagination = new PaginationResponse<>();
        pagination.setItems(List.of(productResponse));
        pagination.setPageNumber(1);
        pagination.setPageSize(25);
        pagination.setHasNext(false);
        pagination.setHasPrevious(false);

        when(productUseCase.searchProductsByCategory(1, 25, "DESC", 1L)).thenReturn(pagination);

        ResponseEntity<PaginationResponse<ProductResponse>> response =
                productController.searchProductsByCategory(1, 25, "DESC", 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getItems().size());
    }
}