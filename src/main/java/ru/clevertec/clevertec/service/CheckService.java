package ru.clevertec.clevertec.service;

import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.model.Check;

import java.util.List;

public interface CheckService {

    Check generationCheck (List<String> item) throws CheckException;
    String generationCheckInString (List<String> item) throws CheckException;

    String generationCheckInHTML (List<String> item) throws CheckException;

    byte[] generationCheckInFile (List<String> item) throws CheckException;

}
