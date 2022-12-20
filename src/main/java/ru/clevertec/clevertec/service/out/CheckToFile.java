package ru.clevertec.clevertec.service.out;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;


@Service
@Slf4j
public class CheckToFile {

        public byte[] getPdf(Check check){

            log.debug("Get Pdf by check = {}", check);

            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                PdfWriter.getInstance(document, out);
                document.open();
                header(document);
                body(document, check);
            } catch(DocumentException e) {
                log.error("DocumentException: PDF file generation error");
            } finally {
                document.close();
            }
            return out.toByteArray();
        }

        private void header(Document document) throws DocumentException {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
            Paragraph paragraphData = new Paragraph(date.format(dateTime), font);
            paragraphData.setAlignment(Element.ALIGN_RIGHT);

            document.add(paragraphData);
            document.add(Chunk.NEWLINE);
        }


        private void body(Document document, Check check) throws DocumentException {

            // создание таблицы ->
            PdfPTable table = new PdfPTable(4);

            // добавление шапки таблицы  ->
            Stream.of("QTY", "DESCRIPTION", "PRICE", "TOTAL")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            // вывод значений изменения цены

                for (Product item: check.getProduct()) {

                    createCell(table, String.valueOf(item.getCount()));
                    createCell(table, item.getName());
                    createCell(table, toCurrency(item.getCost()));
                    createCell(table, toCurrency(item.getTotalCost()));

                    if (item.isPromotion() & item.getCount() >=5) {
                        createCell2(table, "Promotion 5+ -10%");
                        createCell(table, toCurrency(item.getTotalCost()-item.getPromotionCost()));
                    }
                }

            createCell2(table, "TOTAL NO DISCOUNT");
            createCell(table, toCurrency(check.getTotalCost()));

            if (check.getCard() != null) {
                createCell2(table, "DISCOUNT " + check.getCard().getDiscount() + "%");
                createCell(table, toCurrency(check.getTotalPromotion()));
            } else {
                createCell2(table, "NO DISCOUNT CARD ");
                createCell(table, "");
            }

            createCell2(table, "TOTAL");
            createCell(table, toCurrency(check.getTotalCost() - check.getTotalPromotion()));


                document.add(table);

        }


        //создание ячеек таблицы
        private void createCell (PdfPTable table, String str){
            PdfPCell cell = new PdfPCell(new Phrase(str));
            cell.setPaddingLeft(4);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
        }

    private void createCell2 (PdfPTable table, String str){
        PdfPCell cell = new PdfPCell(new Phrase(str));
        cell.setPaddingLeft(4);
        cell.setColspan(3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }

    private String toCurrency (int price) {
        return String.format("$%d.%02d", price/100, price%100);
    }
}
