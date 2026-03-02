package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        Product savedProduct = productService.create(product);

        assertEquals(product.getProductId(), savedProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testCreateProductWithNullId() {
        Product newProduct = new Product();
        newProduct.setProductName("Sampo Cap Botak");
        newProduct.setProductQuantity(10);

        Product savedProduct = productService.create(newProduct);

        assertNotNull(savedProduct.getProductId()); // Harus tidak null (sudah di-generate oleh Service)
        assertEquals("Sampo Cap Botak", savedProduct.getProductName());
        verify(productRepository, times(1)).create(newProduct);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getProductId(), result.get(0).getProductId());
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product result = productService.findById(product.getProductId());

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testFindByIdIfNotFound() {
        when(productRepository.findById("id-yang-tidak-ada")).thenReturn(null);

        Product result = productService.findById("id-yang-tidak-ada");

        assertNull(result);
        verify(productRepository, times(1)).findById("id-yang-tidak-ada");
    }

    @Test
    void testUpdate() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Sampo Cap Bambang Baru");
        updatedProduct.setProductQuantity(200);

        when(productRepository.update(product.getProductId(), updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(product.getProductId(), updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        verify(productRepository, times(1)).update(product.getProductId(), updatedProduct);
    }

    @Test
    void testDelete() {
        productService.deleteProductById(product.getProductId());

        verify(productRepository, times(1)).delete(product.getProductId());
    }
}