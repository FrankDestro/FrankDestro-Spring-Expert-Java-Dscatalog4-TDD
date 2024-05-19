package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.ProductFactory;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks  // referenciando a classe a ser testada.
    private ProductService service;

    @Mock
    // Instanciando uma dependência de forma Mockada, obd. Quando você cria o Mock você deve configurar o comportamento simulado dele
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;

    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO productDTO;
    private Category category;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        category = ProductFactory.createCategory();
        product = ProductFactory.createProduct();
        productDTO = ProductFactory.createProductDTO();
        page = new PageImpl<>(List.of(product)); // instancia um objeto page do tipo PageImpl<> adicionnado um produto na lista.

        // Configuração do comportamento Simulado do repository para teste de unidade.

        // findAll
        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        //Insert
        when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        // findById
        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));
        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Delete
        doNothing().when(repository).deleteById(existingId);
        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        // Update
        // Product
        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        // Category
        when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

    }

    @Test
    public void UpdateShouldReturnProductDTOWhenIdExists() {
        productDTO = service.update(existingId, productDTO);
        assertNotNull(productDTO);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, productDTO);
        });
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> {
           service.findById(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        productDTO = service.findById(existingId);
        assertNotNull(productDTO);
        verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        assertNotNull(result);
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
        verify(repository, times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(repository, times(1)).existsById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        verify(repository, times(1)).deleteById(existingId);
    }
}
