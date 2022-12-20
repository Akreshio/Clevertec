package ru.clevertec.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Product {

    private String name;
    private int cost;
    private boolean promotion;
    private int count;
    private int totalCost;
    private int promotionCost;
}
