package com.devsuperior.examplemockspy.services;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.factory.ProductFactory;
import com.devsuperior.examplemockspy.repositories.ProductRepository;
import com.devsuperior.examplemockspy.services.exceptions.InvalidDataException;
import com.devsuperior.examplemockspy.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        productDTO = ProductFactory.createProductDTO();
        product = ProductFactory.createProduct();
        ProductService serviceSpy = spy(service);

        // insert
        when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        // update
        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        // simulação do comportamento do validateData com o @spy
        doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

    }

    @Test
    public void insertShouldReturnProductDTOValidData() {

        ProductService serviceSpy = spy(service);

        productDTO = serviceSpy.insert(productDTO);
        assertNotNull(productDTO);
        assertEquals(productDTO.getName(), product.getName());
    }


    @Test
    public void insertShouldThrowInvalidDataExceptionWhenProductNameIsBlank() {

        productDTO.setName("");
        ProductService serviceSpy = spy(service);

        assertThrows(InvalidDataException.class, () -> {
            @SuppressWarnings("unused")
            ProductDTO result = serviceSpy.insert(productDTO);
        });
    }

    @Test
    public void insertShouldThrowInvalidDataExceptionWhenProductPriceIsNegativeOrZero2() {

        productDTO.setPrice(-5.0);
        ProductService serviceSpy = spy(service);

        assertThrows(InvalidDataException.class, () -> {
            serviceSpy.validateData(productDTO);
        });
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExistsAndValidData() {
        ProductService serviceSpy = spy(service);

        ProductDTO result = serviceSpy.update(existingId, productDTO);
        assertNotNull(result);
    }


    @Test
    public void updateShouldReturnInvalidDataExceptionWhenIdExistsAndProductNameIsBlank() {

        productDTO.setName("");
        ProductService serviceSpy = spy(service);

        assertThrows(InvalidDataException.class, () -> {
            serviceSpy.validateData(productDTO);
            ProductDTO result = serviceSpy.update(existingId, productDTO);
        });
    }

    @Test
    public void updateShouldReturnInvalidDataExceptionWhenIdExistsAndProductPriceIsNegativeOrZero() {

        productDTO.setPrice(-5.0);
        ProductService serviceSpy = spy(service);

        assertThrows(InvalidDataException.class, () -> {
            serviceSpy.validateData(productDTO);
            ProductDTO result = serviceSpy.update(existingId, productDTO);
        });
    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExistsAndValidData() {

        ProductService serviceSpy = spy(service);

        assertThrows(ResourceNotFoundException.class, () -> {
            serviceSpy.validateData(productDTO);
            ProductDTO result = serviceSpy.update(nonExistingId, productDTO);
        });
    }

}
