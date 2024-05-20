package com.devsuperior.examplemockspy.factory;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product(1L, "Phone", 2000.0);
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product);
    }
}
