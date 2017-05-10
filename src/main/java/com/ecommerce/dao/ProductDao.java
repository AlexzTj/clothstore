package com.ecommerce.dao;


import com.ecommerce.model.Product;
import com.ecommerce.model.Image;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductDao {
    private static List<Product> dummylist = new ArrayList<>();
    public static Product p1 = new Product();
    public static Product p2 = new Product();

    static {
        Image pm1 = new Image();
        Image pm2 = new Image();
        pm1.setId(1);
        pm1.setProduct(p1);
        pm1.setMainImage("\\resources\\img\\detailbig1.jpg");
        pm1.setThumbnail("\\resources\\img\\detailsquare.jpg");

        p1.setCategory(CategoryDao.cat);
        p1.setDescription("product 1 description");
        p1.setId(1);
        p1.setPrice(200D);
        p1.setTitle("Jeans");
        Set<Image> s1 = new HashSet<>();
        s1.add(pm1);
        p1.setImageMetaSet(s1);

        p2.setCategory(CategoryDao.cat2);
        p2.setDescription("product 2 description");
        p2.setId(2);
        p2.setPrice(120D);
        p2.setTitle("Shoes");
        Set<Image> s2 = new HashSet<>();
        s2.add(pm1);
        p2.setImageMetaSet(s2);


        pm2.setId(2);
        pm2.setProduct(p2);
        pm2.setMainImage("\\resources\\img\\detailbig2.jpg");
        pm2.setThumbnail("\\resources\\img\\detailsquare2.jpg");

        dummylist.add(p1);
        dummylist.add(p2);
    }

    public Product getProductById(Integer productId) {
        Optional<Product> result = dummylist.stream().filter(p -> p.getId().equals(productId)).findFirst();
        return result.orElseThrow(IllegalArgumentException::new);
    }

    public List<Product> getAllProducts() {
        return dummylist;
    }
}
