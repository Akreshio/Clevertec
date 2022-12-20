package ru.clevertec.clevertec.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void testCreateCard() {
        Card card = new Card(0);
        Assertions.assertEquals(0, card.getDiscount());
    }

    @Test
    void testBuilderCard() {
        Card card = Card.builder().discount(20).build();
        Assertions.assertEquals(20, card.getDiscount());}
}

