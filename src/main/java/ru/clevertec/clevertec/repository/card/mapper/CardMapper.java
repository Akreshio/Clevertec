package ru.clevertec.clevertec.repository.card.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertec.repository.card.entity.CardEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CardMapper implements RowMapper<CardEntity> {
    @Override
    public CardEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CardEntity
                .builder()
                .id(rs.getLong("id"))
                .discount(rs.getInt("discount"))
                .build();
    }
}
