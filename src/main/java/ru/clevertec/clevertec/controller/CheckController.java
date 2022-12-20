package ru.clevertec.clevertec.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.service.CheckService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CheckController {

    CheckService checkService;

    //http://localhost:8080/check?item=2-2,22-7,24-1,12-8,18-9,17-1,card-1
    @GetMapping("/check")
    @ResponseBody
    public String check(@RequestParam List<String> item) throws CheckException {
        for (String str : item) {
            if (!((str.matches("\\d+-\\d+")) || (str.matches("card-\\d+"))))
                throw new CheckException("request contains invalid data", HttpStatus.BAD_REQUEST);
        }

        return checkService.generationCheckInHTML(item);
    }

    //http://localhost:8080/check/file?item=2-2,22-7,24-1,12-8,18-9,17-1,card-1
    @GetMapping("/check/file")
    @ResponseBody
    public ResponseEntity<byte[]> checkInFile(@RequestParam List<String> item) throws CheckException {
        for (String str : item) {
            if (!((str.matches("\\d+-\\d+")) || (str.matches("card-\\d+"))))
                throw new CheckException("request contains invalid data", HttpStatus.BAD_REQUEST);
        }
        HttpHeaders httpHeaders = getHttpHeaders();
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(
                        checkService.generationCheckInFile(item)
                );
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
                .attachment()
                .filename("check_" + LocalDateTime.now() + ".pdf")
                .build()
                .toString()
        );
        return httpHeaders;
    }
}
