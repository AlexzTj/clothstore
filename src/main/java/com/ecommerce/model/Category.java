package com.ecommerce.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    @Column(updatable = false, nullable = false)
    private Integer id;
    @Column(nullable = false, unique = true)
    @Size(max = 40)
    @Pattern(regexp = "^[a-z0-9-]+$", message = "must be valid pattern /^[a-z0-9-]+$/")
    @NotEmpty(message = "please specify category slug")
    private String slug;
    @Size(max = 100)
    @NotEmpty(message = "please specify category name")
    private String name;
    @Size(max = 240)
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Product> productSet = Collections.emptySet();

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void addProduct(Product product) {
        productSet.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        productSet.remove(product);
        product.setCategory(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;
        return Objects.equals(getName(), category.getName());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
