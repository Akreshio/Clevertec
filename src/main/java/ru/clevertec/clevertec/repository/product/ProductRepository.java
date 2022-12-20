package ru.clevertec.clevertec.repository.product;

import ru.clevertec.clevertec.repository.product.entity.ProductEntity;

public interface ProductRepository {

    ProductEntity get(Long id);
    boolean add(ProductEntity entity);
    boolean delete(Long id);


}
