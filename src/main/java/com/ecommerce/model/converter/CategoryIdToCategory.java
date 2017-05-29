package com.ecommerce.model.converter;

import com.ecommerce.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdToCategory implements Converter<String, Category> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryIdToCategory.class);

    @Override
    public Category convert(String id) {
        try {
            Category x = new Category();
            x.setId(Integer.parseInt(id));
            return x;
        } catch (Exception e) {
            LOGGER.error("exception occurred during CategoryId To Category conversion", e);
            return null;
        }
    }
}
