package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks  // referenciando a classe a ser testada.
    private ProductService service;

    @Mock  // ao usar o Mock voce deve configurar o comportamento simulado dele.
    private ProductRepository repository;

    private long existingId;
    private long noExistingId;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        noExistingId = 1000L;

        Mockito.doNothing().when(repository).deleteById(existingId); // criando um comportamento simulado do repository quando id existir
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(noExistingId);  // criando um comportamento simulado do repository quando id não existir
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }
}
