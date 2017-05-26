package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    @Column(updatable = false, nullable = false)
    private Integer id;
    @NotBlank(message = "please specify product name")
    @Size(max = 100)
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "please specify product code")
    @Size(max = 10)
    private String productCode;
    @Size(max = 250)
    private String description;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime timestamp;
    @Column(nullable = false)
    @NotNull
    @Digits(message = "must have proper format", integer = 5, fraction = 2)
    @Min(value = 0, message = "price can not be negative value")
    private Double price;
    @Column(name = "special")
    private boolean onSpecialOffer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Image> imageMetaSet = Collections.emptyList();

    public Product() {
    }

    public boolean isOnSpecialOffer() {
        return onSpecialOffer;
    }

    public void setOnSpecialOffer(boolean onSpecialOffer) {
        this.onSpecialOffer = onSpecialOffer;
    }

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

    public List<Image> getImageMetaSet() {
        return imageMetaSet;
    }

    public void setImageMetaSet(List<Image> imageMetaSet) {
        this.imageMetaSet = imageMetaSet;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void addImage(Image image) {
        imageMetaSet.add(image);
        image.setProduct(this);
    }

    public void removeImage(Image image) {
        imageMetaSet.remove(image);
        image.setProduct(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(getProductCode(), product.getProductCode());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getProductCode()).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
