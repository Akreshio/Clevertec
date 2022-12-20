package ru.clevertec.clevertec.service.formation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.clevertec.exception.CheckException;
import ru.clevertec.clevertec.model.Card;
import ru.clevertec.clevertec.model.Check;
import ru.clevertec.clevertec.model.Product;
import ru.clevertec.clevertec.repository.card.CardRepository;
import ru.clevertec.clevertec.repository.card.entity.CardEntity;
import ru.clevertec.clevertec.repository.product.ProductRepository;
import ru.clevertec.clevertec.repository.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CheckCreatingImpl implements CheckCreating{
    CardRepository cardRepository;
    ProductRepository productRepository;

    @Override
    public Check create(List<String> item) throws CheckException {

        Map<Long, Product> products = new LinkedHashMap<>();
        Card card = null;

        for (String i : item) {
            String[] str = i.split("-");

            if (str[0].matches("\\d+")) {
                Long id = Long.valueOf(str[0]);
                int count = Integer.parseInt(str[1]);
                int costProduct;
                int promotion = 0;

                if (products.get(id) == null) {
                    ProductEntity productEntity = productRepository.get(id);
                    if  (productEntity == null)
                        throw new CheckException("error when sharing product with id: " + id, HttpStatus.BAD_REQUEST);

                    costProduct = count * productEntity.getCost();
                    if ((productEntity.isPromotion()) & (count >= 5))
                        promotion = costProduct/10;

                    Product product = new Product(
                        productEntity.getName(),
                        productEntity.getCost(),
                        productEntity.isPromotion(),
                        count,
                        costProduct,
                        promotion
                    );

                    products.put(id, product);
                } else {
                    Product product = products.get(id);
                    product.setCount(product.getCount() + count);

                    product.setTotalCost(product.getCount() * product.getCost());

                    if ((product.isPromotion()) & (product.getCount() >= 5))
                        product.setPromotionCost(product.getTotalCost()/10);
                    products.put(id, product);
                }
            } else {
                if (str[0].equals("card")) {
                    CardEntity cardEntity = cardRepository.get(Long.valueOf(str[1]));
                    if (cardEntity == null)
                        throw new CheckException(" Card with number " + str[1] + " not found" , HttpStatus.BAD_REQUEST);
                    card = Card.builder().discount(cardEntity.getDiscount()).build();
                } else {
                    throw new CheckException("" , HttpStatus.BAD_REQUEST);
                }
            }
        }
        int totalCost = 0;
        int totalPromotion = 0;

        List<Product> productList = new ArrayList<>(products.values());

        for (Product product :productList)
        {
            totalCost += product.getTotalCost() - product.getPromotionCost();
        }

        if (card != null) totalPromotion = totalCost*card.getDiscount()/100;

        return new Check(productList,card,totalCost,totalPromotion);
    }
}
