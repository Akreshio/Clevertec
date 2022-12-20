package ru.clevertec.clevertec.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.service.CheckService;
import java.util.List;

import static org.mockito.Mockito.*;

class CheckControllerTest {
    @Mock
    CheckService checkService;
    @InjectMocks
    CheckController checkController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheck() {
        try {
            when(checkService.generationCheckInHTML(any())).thenReturn("generationCheckInHTMLResponse");


        String result = checkController.check(List.of("0-1"));
        Assertions.assertEquals("generationCheckInHTMLResponse", result);

        } catch (CheckException e) {
        throw new RuntimeException(e);
    }
    }
}