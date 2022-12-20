package ru.clevertec.clevertec.service.out;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;
import java.util.List;

class CheckToFileTest {
    @Mock
    Logger log;
    @InjectMocks
    CheckToFile checkToFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPdf() {
        byte[] bytes = new byte[1462];

        byte[] result = checkToFile.getPdf(new Check(List.of(new Product("name", 0, true, 0, 0, 0)), null, 0, 0));
        Assertions.assertEquals(bytes.length, result.length);
    }
}