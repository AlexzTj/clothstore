package com.ecommerce.dao;


import com.ecommerce.model.Product;

import java.util.List;

public interface ProductDao {

    void addProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Integer productId);

    Product getProductById(Integer id, boolean fetchAll);

    List<Product> getAllProducts();

    List<Product> fetchProductsWithFeaturedImage(int size);
}
