package ru.clevertec.clevertec.repository.card;

import ru.clevertec.clevertec.repository.card.entity.CardEntity;

public interface CardRepository {
    CardEntity get (Long id);
    boolean add(CardEntity entity);
    boolean delete (Long id);
}
