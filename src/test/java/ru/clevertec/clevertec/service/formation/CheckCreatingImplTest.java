package ru.clevertec.clevertec.service.formation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.model.Card;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;
import ru.clevertec.clevertec.repository.card.CardRepository;
import ru.clevertec.clevertec.repository.card.entity.CardEntity;
import ru.clevertec.clevertec.repository.product.ProductRepository;
import ru.clevertec.clevertec.repository.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;



class CheckCreatingImplTest {
    @Mock
    CardRepository cardRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    Logger log;
    @InjectMocks
    CheckCreatingImpl checkCreatingImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = Product.builder()
                .name("name")
                .count(1)
                .cost(100)
                .promotion(false)
                .promotionCost(0)
                .totalCost(100).build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Check check = Check.builder().product(productList).totalCost(100).build();


        when(cardRepository.get(anyLong())).thenReturn(new CardEntity(1L, 0));
        when(productRepository.get(anyLong())).thenReturn(new ProductEntity(1L, "name", 100, false));



        Check result;
        try {
            result = checkCreatingImpl.create(List.of("1-1"));
        } catch (CheckException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(check, result);
    }
    @Test
    void testCreateDiscont() {
        Product product = Product.builder()
                .name("name")
                .count(5)
                .cost(100)
                .promotion(true)
                .promotionCost(50)
                .totalCost(500).build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Check check = Check.builder().product(productList).totalCost(450).build();


        when(cardRepository.get(anyLong())).thenReturn(new CardEntity(1L, 0));
        when(productRepository.get(anyLong())).thenReturn(new ProductEntity(1L, "name", 100, true));



        Check result;
        try {
            result = checkCreatingImpl.create(List.of("1-5"));
        } catch (CheckException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(check, result);
    }
    @Test
    void testDuableProduct() {
        Product product = Product.builder()
                .name("name")
                .count(5)
                .cost(100)
                .promotion(true)
                .promotionCost(50)
                .totalCost(500).build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Check check = Check.builder().product(productList).totalCost(450).build();


        when(cardRepository.get(anyLong())).thenReturn(new CardEntity(1L, 0));
        when(productRepository.get(anyLong())).thenReturn(new ProductEntity(1L, "name", 100, true));



        Check result;
        try {
            result = checkCreatingImpl.create(List.of("1-2","1-3"));
        } catch (CheckException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(check, result);
    }

    @Test
    void testCard() {
        Product product = Product.builder()
                .name("name")
                .count(2)
                .cost(100)
                .promotion(true)
                .promotionCost(0)
                .totalCost(200).build();
        Card card = Card.builder().discount(20).build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Check check = Check.builder().product(productList).totalCost(200).card(card).totalPromotion(40).build();


        when(cardRepository.get(anyLong())).thenReturn(new CardEntity(1L, 20));
        when(productRepository.get(anyLong())).thenReturn(new ProductEntity(1L, "name", 100, true));



        Check result;
        try {
            result = checkCreatingImpl.create(List.of("1-2","card-1"));
        } catch (CheckException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(check, result);
    }

}