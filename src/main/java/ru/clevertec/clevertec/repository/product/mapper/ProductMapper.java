package ru.clevertec.clevertec.repository.product.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertec.repository.product.entity.ProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper implements RowMapper<ProductEntity> {

    @Override
    public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductEntity
                .builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .cost(rs.getInt("cost"))
                .promotion(rs.getBoolean("promotion"))
                .build();
    }
}
