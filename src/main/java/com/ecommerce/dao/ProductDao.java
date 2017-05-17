package com.ecommerce.dao;


import com.ecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductDao {

    void addProduct(Product product);

    void deleteProduct(Integer productId);

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    List<Product> fetchProductsWithFeaturedImage(int size);
}
