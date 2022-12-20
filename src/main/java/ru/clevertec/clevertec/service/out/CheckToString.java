package ru.clevertec.clevertec.service.out;

import org.springframework.stereotype.Component;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CheckToString {

    public String toString(Check check) {

        StringBuilder str = new StringBuilder();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        str.append(String.format("\n%32s%s\n%s\n%s%12s%22s%8s\n",
                "DATE:", date.format(dateTime),
                "_______________________________________________",
                "QTY", "DESCRIPTION", "PRICE", "TOTAL"));

        for(Product item : check.getProduct()){
            if (item.isPromotion() & item.getCount() >=5) {
                str.append(
                        String.format("\n%-4d%-26s\t%s\t%s\n%25s%-10s\t\t%s\n",
                                item.getCount(),
                                item.getName(),
                                toCurrency(item.getCost()),
                                toCurrency(item.getTotalCost()),
                                "Promotion 5+ -10% ", " ",
                                toCurrency(item.getTotalCost()-item.getPromotionCost()))
                );
            } else {
                str.append(String.format("\n%-4d%-24s\t%s\t%s\n", item.getCount(), item.getName(),
                        toCurrency(item.getCost()), toCurrency(item.getTotalCost())));
            }
        }

        if (check.getCard() != null) {
            str.append(String.format("%s\n%s%30s\n%s%4d%%%34s\n%s%42s",
                    "_______________________________________________",
                    "TOTAL NO DISCOUNT", toCurrency(check.getTotalCost()),
                    "DISCOUNT", check.getCard().getDiscount(), toCurrency(check.getTotalPromotion()),
                    "TOTAL", toCurrency(check.getTotalCost() - check.getTotalPromotion())));
        } else {
            str.append(String.format("\n%s\n%-39s\t%s\n%s%30s%d\n%s%42s",
                    "_______________________________________________",
                    "TOTAL NO DISCOUNT", toCurrency(check.getTotalCost()),
                    "NO DISCOUNT CARD", "$", 0,
                    "TOTAL", toCurrency(check.getTotalCost())));
        }
        return str.toString();
    }


    public String toStringHTML(Check check) {

        StringBuilder str = new StringBuilder();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        str.append("<table> <tbody> <tr> <td></td> <td></td> <td colspan=\"2\"> DATE:")
                .append(date.format(dateTime))
                .append("</td> </tr>");

        str.append("<tr><td>QTY</td><td>DESCRIPTIO</td><td>PRICE</td><td>TOTAL</td>");

        for(Product item : check.getProduct()){
            str.append("<tr><td>")
                    .append(item.getCount())
                    .append("</td><td>")
                    .append(item.getName())
                    .append("</td><td>")
                    .append(toCurrency(item.getCost()))
                    .append("</td><td>")
                    .append(toCurrency(item.getTotalCost()))
                    .append("</td>");

            if (item.isPromotion() & item.getCount() >=5) {
                str.append("<tr><td></td><td>Promotion 5+ -10%</td><td>")
                        .append("</td><td>")
                        .append(toCurrency(item.getTotalCost()-item.getPromotionCost()))
                        .append("</td>");
            }
        }
        str.append("<tr><td colspan=\"3\">TOTAL NO DISCOUNT</td><td>")
                .append(toCurrency(check.getTotalCost()))
                .append("</td></tr>");

        if (check.getCard() != null) {
            str.append("<tr><td colspan=\"3\">DISCOUNT ")
                    .append(check.getCard().getDiscount())
                    .append("% </td><td>")
                    .append(toCurrency(check.getTotalPromotion()))
                    .append("</td></tr>");
        } else {
            str.append("<tr><td colspan=\"3\">NO DISCOUNT CARD</td><td></td></tr>");
        }

        str.append("<tr><td colspan=\"3\">TOTAL</td><td>")
                .append(toCurrency(check.getTotalCost() - check.getTotalPromotion()))
                .append("</td></tr></tbody></table>");

        return str.toString();
    }

    private String toCurrency (int price) {
        return String.format("$%d.%02d", price/100, price%100);
    }
}
