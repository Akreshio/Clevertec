package ru.clevertec.clevertec.service.formation;

import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.model.Check;

import java.util.List;

public interface CheckCreating {

    Check create (List<String> item) throws CheckException;

}
