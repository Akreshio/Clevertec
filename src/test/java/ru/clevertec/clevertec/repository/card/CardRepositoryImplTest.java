package ru.clevertec.clevertec.repository.card;

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
import ru.clevertec.clevertec.repository.card.entity.CardEntity;
import ru.clevertec.clevertec.repository.card.mapper.CardMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CardRepositoryImplTest {
    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;
    @Mock
    CardMapper cardMapper;
    @Mock
    Logger log;
    @InjectMocks
    CardRepositoryImpl cardRepositoryImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        List<CardEntity> list = new ArrayList<>();
        CardEntity card = new CardEntity(1L, 0);
        list.add(card);

        Mockito.when(
                    jdbcTemplate.query(any(),any(MapSqlParameterSource.class),any(CardMapper.class)))
                .thenReturn(list);
        CardEntity result = cardRepositoryImpl.get(1L);
        Assertions.assertEquals(card, result);
    }

    @Test()
    void testGetException() {
        Mockito.when(
                    jdbcTemplate.query(any(), any(MapSqlParameterSource.class), any(CardMapper.class)))
               .thenThrow(new DataAccessException("test Exception") {});

        CardEntity result = cardRepositoryImpl.get(1L);
        Assertions.assertNull(result);
    }


    @Test
    void testAdd() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenReturn(1);

        boolean result = cardRepositoryImpl.add(new CardEntity(1L, 0));
        Assertions.assertTrue(result);
    }

    @Test
    void testAddException() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenThrow(new DataAccessException("test Exception") {});

        boolean result = cardRepositoryImpl.add(new CardEntity(1L, 0));
        Assertions.assertFalse(result);
    }

    @Test
    void testDelete() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenReturn(1);

        boolean result = cardRepositoryImpl.delete(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteException() {
        Mockito.when(
                        jdbcTemplate.update(any(),any(MapSqlParameterSource.class)))
                .thenThrow(new DataAccessException("test Exception") {});

        boolean result = cardRepositoryImpl.delete(1L);
        Assertions.assertFalse(result);
    }
}