package com.ecommerce.service.util;


import com.ecommerce.model.Product;

import java.time.LocalDateTime;

public final class ProductUtils {
    public static boolean isNew(Product product) {
        LocalDateTime specificDate = LocalDateTime.now().minusDays(5);
        return product.getTimestamp() != null && product.getTimestamp().isAfter(specificDate);
    }
}
