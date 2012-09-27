package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

import java.util.List;

/**
 * @author damienriccio
 */
public class Product extends GenericIdentifiable {
    private String nameKey;
    private String descriptionKey;
    private String token;
    private List<ProductCategory> productCategories;

    //todo add picture
    //todo add barcode


    public Product() {
    }

    public Product(Long id) {
        super(id);
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (descriptionKey != null ? !descriptionKey.equals(product.descriptionKey) : product.descriptionKey != null) return false;
        if (nameKey != null ? !nameKey.equals(product.nameKey) : product.nameKey != null) return false;
        if (productCategories != null ? !productCategories.equals(product.productCategories) : product.productCategories != null)
            return false;
        if (token != null ? !token.equals(product.token) : product.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nameKey != null ? nameKey.hashCode() : 0;
        result = 31 * result + (descriptionKey != null ? descriptionKey.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (productCategories != null ? productCategories.hashCode() : 0);
        return result;
    }
}
