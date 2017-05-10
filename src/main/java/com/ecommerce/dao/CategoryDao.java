package com.ecommerce.dao;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CategoryDao {
    private static List<Category> list = new ArrayList<>();
    static public Category cat = new Category();
    static public Category cat2 = new Category();

    static {
        cat.setId(1);
        cat.setName("Men");
        cat.setDescription("cat1 description");
        cat2.setId(2);
        cat2.setName("Women");
        Set<Product> s1 = new HashSet<>();
        cat.setProductSet(s1);
        s1.add(ProductDao.p1);
        Set<Product> s2 = new HashSet<>();
        cat2.setProductSet(s2);
        cat2.setDescription("cat2 description");
        s2.add(ProductDao.p2);
        list.add(cat);
        list.add(cat2);
    }

    public Category getCategoryById(Integer id) {
        Optional<Category> result = list.stream().filter(c -> c.getId().equals(id)).findFirst();
        return result.orElseThrow(IllegalArgumentException::new);
    }
}
