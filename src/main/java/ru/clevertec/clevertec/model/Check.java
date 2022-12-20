package ru.clevertec.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Builder
public class Check {

    private List<Product> product;
    private Card card;
    private int totalCost;
    private int totalPromotion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return totalCost == check.totalCost && totalPromotion == check.totalPromotion && Objects.equals(product, check.product) && Objects.equals(card, check.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, card, totalCost, totalPromotion);
    }
}
