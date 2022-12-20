package ru.clevertec.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Builder
public class Card {
    private int discount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return discount == card.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discount);
    }
}
