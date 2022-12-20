package ru.clevertec.clevertec.repository.card;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.clevertec.repository.card.mapper.CardMapper;
import ru.clevertec.clevertec.repository.card.entity.CardEntity;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class CardRepositoryImpl implements CardRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CardMapper cardMapper;
    private static final String TABLE = "discount_card";

    @Override
    public CardEntity get(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        String query = "select * from " + TABLE + " where id = :id";

        try {
            List<CardEntity> list = jdbcTemplate.query(query,parameterSource,cardMapper);
            if (list.size() != 1)  return null;
            return list.get(0);
        } catch (DataAccessException e){
            log.debug("error when sharing card with id: " + id);
            return null;
        }
    }

    @Override
    public boolean add(CardEntity entity) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("discount", entity.getDiscount());
        String query = "insert into " + TABLE + " where discount = :discount";

        try {
            jdbcTemplate.update(query, parameterSource);
        } catch (DataAccessException e){
            log.debug("error when adding a card");
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
            log.debug("error when deleting a card with id: " + id);
            return false;
        }
        return true;
    }
}
