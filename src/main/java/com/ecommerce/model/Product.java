package com.ecommerce.model;


import java.util.Collections;
import java.util.Set;

public class Product {

    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Category category;
    private Set<Image> imageMetaSet = Collections.emptySet();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Image> getImageMetaSet() {
        return imageMetaSet;
    }

    public void setImageMetaSet(Set<Image> imageMetaSet) {
        this.imageMetaSet = imageMetaSet;
    }
}
