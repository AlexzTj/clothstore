package com.ecommerce.service;


import com.ecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    void addProductWithImages(Product product, List<MultipartFile> images);

    void updateProduct(Product product, List<MultipartFile> images);

    void deleteProduct(Integer productId);

    Product getProductById(Integer id);

    Product getProductById(Integer id, boolean fetchAll);

    List<Product> getAllProducts();

    List<Product> fetchProductsWithFeaturedImage(int size);
}
