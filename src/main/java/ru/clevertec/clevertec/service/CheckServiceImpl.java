package ru.clevertec.clevertec.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.service.formation.CheckCreating;
import ru.clevertec.clevertec.service.out.CheckToFile;
import ru.clevertec.clevertec.service.out.CheckToString;

import java.util.List;

@Service
@AllArgsConstructor
public class CheckServiceImpl implements CheckService {

    private CheckCreating checkCreating;
    private CheckToString checkToString;
    private CheckToFile toFile;

    @Override
    public Check generationCheck (List<String> item) throws CheckException {
        return checkCreating.create(item);
    }

    @Override
    public String generationCheckInString (List<String> item) throws CheckException {
        return checkToString.toString(checkCreating.create(item));
    }

    @Override
    public String generationCheckInHTML (List<String> item) throws CheckException {
        return checkToString.toStringHTML(checkCreating.create(item));
    }

    @Override
    public byte[] generationCheckInFile(List<String> item) throws CheckException {
        return toFile.getPdf(checkCreating.create(item));
    }
}
