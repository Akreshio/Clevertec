package ru.clevertec.clevertec.repository.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.clevertec.repository.product.entity.ProductEntity;
import ru.clevertec.clevertec.repository.product.mapper.ProductMapper;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;
    private static final String TABLE = "product";


    @Override
    public ProductEntity get(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        String query = "select * from " + TABLE + " where id = :id";

        try {
            List<ProductEntity> list = jdbcTemplate.query(query,parameterSource,productMapper);
            if (list.size() != 1)  return null;
            return list.get(0);
        } catch (DataAccessException e){
            log.debug("error when sharing product with id: " + id);
            return null;
        }
    }

    @Override
    public boolean add(ProductEntity entity) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", entity.getName());
        parameterSource.addValue("cost", entity.getCost());
        parameterSource.addValue("promotion", entity.isPromotion());
        String query = "insert into " + TABLE + " where name = :name and cost = :cost and promotion = :promotion";

        try {
            jdbcTemplate.update(query, parameterSource);
        } catch (DataAccessException e){
            log.debug("error when adding a product with name: " + entity.getName());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        String query = "delete from " + TABLE + " where id = :id";

        try {
            jdbcTemplate.update(query,parameterSource);
        } catch (DataAccessException e){
            log.debug("error when deleting a product with id: " + id);
            return false;
        }
        return true;
    }
}
