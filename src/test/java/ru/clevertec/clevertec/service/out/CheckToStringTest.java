package ru.clevertec.clevertec.service.out;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.clevertec.model.Card;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class CheckToStringTest {
    CheckToString checkToString = new CheckToString();

    @Test
    void testToString() {
        Product product = Product.builder()
                .name("name")
                .count(5)
                .cost(100)
                .promotion(true)
                .promotionCost(50)
                .totalCost(500).build();
        Card card = Card.builder().discount(10).build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Check check = Check.builder().product(productList).totalCost(450).card(card).totalPromotion(45).build();

        String result = checkToString.toString(check);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        String str = String.format("\n%32s%s\n%s\n%s%12s%22s%8s\n\n%-4d%-26s\t%s\t%s\n%25s%-10s\t\t%s\n%s\n%s%30s\n%s%4d%%%34s\n%s%42s",
                "DATE:", date.format(dateTime),
                "_______________________________________________",
                "QTY", "DESCRIPTION", "PRICE", "TOTAL",
                5,
                "name",
                "$1.00",
                "$5.00",
                "Promotion 5+ -10% ", " ","$4.50",
                "_______________________________________________",
                "TOTAL NO DISCOUNT", "$4.50",
                "DISCOUNT", 10, "$0.45",
                "TOTAL", "$4.05");

        Assertions.assertEquals(str, result);
    }

    @Test
    void testToStringHTML() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        String str = "<table> <tbody> <tr> <td></td> <td></td> <td colspan=\"2\"> DATE:"
                + date.format(dateTime)
                + "</td> </tr><tr><td>QTY</td><td>DESCRIPTIO</td><td>PRICE</td><td>TOTAL</td><tr><td>0</td><td>name</td><td>$0.00</td><td>$0.00</td><tr><td colspan=\"3\">TOTAL NO DISCOUNT</td><td>$0.00</td></tr><tr><td colspan=\"3\">DISCOUNT 0% </td><td>$0.00</td></tr><tr><td colspan=\"3\">TOTAL</td><td>$0.00</td></tr></tbody></table>";

        String result = checkToString.toStringHTML(new Check(List.of(new Product("name", 0, true, 0, 0, 0)), new Card(0), 0, 0));
        Assertions.assertEquals(str, result);
    }
}