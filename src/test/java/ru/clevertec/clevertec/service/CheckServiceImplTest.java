package ru.clevertec.clevertec.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.model.Card;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;
import ru.clevertec.clevertec.service.formation.CheckCreating;
import ru.clevertec.clevertec.service.out.CheckToFile;
import ru.clevertec.clevertec.service.out.CheckToString;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CheckServiceImplTest {
    @Mock
    CheckCreating checkCreating;
    @Mock
    CheckToString checkToString;
    @Mock
    CheckToFile toFile;
    @InjectMocks
    CheckServiceImpl checkServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  @Test
    void testGenerationCheck() {
      try {
          Product product = Product.builder()
                  .name("name")
                  .count(1)
                  .cost(100)
                  .promotion(true)
                  .promotionCost(0)
                  .totalCost(200).build();
          Card card = Card.builder().discount(20).build();

          List<Product> productList = new ArrayList<>();
          productList.add(product);
          Check check = Check.builder().product(productList).totalCost(200).card(card).totalPromotion(40).build();

        when(checkCreating.create(any())).thenReturn(check);

        Check result = checkServiceImpl.generationCheck(List.of("0-1"));
        Assertions.assertEquals(check, result);
      } catch (CheckException e) {
          throw new RuntimeException(e);
      }
    }

    @Test
    void testGenerationCheckInString() {
        try {
        when(checkCreating.create(any())).thenReturn(null);
        when(checkToString.toString(any())).thenReturn("toStringResponse");

        String result = checkServiceImpl.generationCheckInString(List.of("1-0"));
        Assertions.assertEquals("toStringResponse", result);
        } catch (CheckException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerationCheckInHTML() {
        try {
        when(checkCreating.create(any())).thenReturn(null);
        when(checkToString.toStringHTML(any())).thenReturn("toStringHTMLResponse");

        String result = checkServiceImpl.generationCheckInHTML(List.of("0-0"));
        Assertions.assertEquals("toStringHTMLResponse", result);
        } catch (CheckException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerationCheckInFile() {
        try {
            when(checkCreating.create(any())).thenReturn(null);
            when(toFile.getPdf(any())).thenReturn(new byte[]{(byte) 0});
            byte[] result = checkServiceImpl.generationCheckInFile(List.of("1-1"));

            Assertions.assertArrayEquals(new byte[]{(byte) 0}, result);

        } catch (CheckException e) {
            throw new RuntimeException(e);
        }

    }
}