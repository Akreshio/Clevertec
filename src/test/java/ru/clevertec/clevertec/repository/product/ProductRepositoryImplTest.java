package ru.clevertec.clevertec.repository.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.clevertec.clevertec.repository.card.mapper.CardMapper;
import ru.clevertec.clevertec.repository.product.entity.ProductEntity;
import ru.clevertec.clevertec.repository.product.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ProductRepositoryImplTest {
    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;
    @Mock
    ProductMapper productMapper;
    @Mock
    Logger log;
    @InjectMocks
    ProductRepositoryImpl productRepositoryImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity product = new ProductEntity(Long.valueOf(1), "name", 0, true);
        list.add(product);

        Mockito.when(
                        jdbcTemplate.query(any(),any(MapSqlParameterSource.class),any(ProductMapper.class)))
                .thenReturn(list);
        ProductEntity result = productRepositoryImpl.get(Long.valueOf(1));
        Assertions.assertEquals(product, result);
    }

    @Test
    void testGetException() {
        Mockito.when(
                        jdbcTemplate.query(any(),any(MapSqlParameterSource.class),any(ProductMapper.class)))
                .thenThrow(new DataAccessException("test Exception") {});
        ProductEntity result = productRepositoryImpl.get(Long.valueOf(1));
        Assertions.assertNull(result);
    }

    @Test
    void testAdd() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenReturn(1);
        boolean result = productRepositoryImpl.add(new ProductEntity(Long.valueOf(1), "name", 0, true));
        Assertions.assertTrue(result);
    }

    @Test
    void testAddException() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenThrow(new DataAccessException("test Exception") {});
        boolean result = productRepositoryImpl.add(new ProductEntity(Long.valueOf(1), "name", 0, true));
        Assertions.assertFalse(result);
    }
    @Test
    void testDelete() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenReturn(1);
        boolean result = productRepositoryImpl.delete(Long.valueOf(1));
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteException() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenThrow(new DataAccessException("test Exception") {});
        boolean result = productRepositoryImpl.delete(Long.valueOf(1));
        Assertions.assertFalse(result);
    }
}