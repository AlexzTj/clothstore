package com.ecommerce.service.impl;


import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Image;
import com.ecommerce.model.ImageType;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private ProductDao productDao;

    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Override
    public void addProductWithImages(Product product, List<MultipartFile> files) {
        processProductWithImages(product,files,productDao::addProduct);
    }

    @Override
    public void updateProduct(Product product, List<MultipartFile> files) {
        processProductWithImages(product,files,productDao::updateProduct);
    }

    private void processProductWithImages(Product product, List<MultipartFile> files,Consumer<Product> operation){
        List<MultipartFile> imagesFiltered = filterImages(files);
        Map<String, MultipartFile> imagesMap = imagesFiltered.stream().collect(Collectors.toMap(
                f -> "attachment_" + System.nanoTime() + "." + StringUtils.getFilenameExtension(f.getOriginalFilename())
                , Function.identity()));
        product.setImageMetaSet(new ArrayList<>());
        setupImages(product, imagesMap);
        //store in db
        operation.accept(product);
        //store in upload folder
        for (Map.Entry<String, MultipartFile> e : imagesMap.entrySet()) {
            try {
                storageService.store(e.getKey(), e.getValue());
            } catch (IOException e1) {
                LOGGER.error("Storing file failed, force rollback transaction ", e);
                throw new RuntimeException(e1);
            }
        }
    }

    private List<MultipartFile> filterImages(List<MultipartFile> files) {
        Predicate<MultipartFile> imgFilter = f -> {
            if (f.isEmpty()) return false;
            String ext = StringUtils.getFilenameExtension(f.getOriginalFilename());
            return ext != null && ext.matches("(jpe?g|png)");
        };

        return files.stream().filter(imgFilter).collect(Collectors.toList());
    }
    
    private void setupImages(Product product, Map<String, MultipartFile> images) {
        for (Map.Entry<String, MultipartFile> image : images.entrySet()) {
            product.addImage(new Image(image.getKey(),
                    image.getValue().getName().startsWith("featured") ? ImageType.FEATURED : ImageType.GALLERY));
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public Product getProductById(Integer id) {
        return getProductById(id, false);
    }

    @Override
    public Product getProductById(Integer id, boolean fetchAll) {
        return productDao.getProductById(id, fetchAll);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public List<Product> fetchProductsWithFeaturedImage(int size) {
        return productDao.fetchProductsWithFeaturedImage(size);
    }

}
