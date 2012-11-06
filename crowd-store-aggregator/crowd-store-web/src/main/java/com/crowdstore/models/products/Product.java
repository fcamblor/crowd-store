package com.crowdstore.models.products;

import com.crowdstore.models.common.GenericIdentifiable;

/**
 * @author damienriccio
 */
public class Product extends GenericIdentifiable {
    private String displayName;
    private String pubToken;
    private String pictureUrl;
    private String barcode;
    private ProductCategoryIdentity categories;

    public Product(){ super(null); }
    public Product(Long id){ super(id); }

    public String getDisplayName() {
        return displayName;
    }

    public Product setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getPubToken() {
        return pubToken;
    }

    public Product setPubToken(String pubToken) {
        this.pubToken = pubToken;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Product setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;

    }

    public String getBarcode() {
        return barcode;
    }

    public Product setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public ProductCategoryIdentity getCategories() {
        return categories;
    }

    public Product setCategories(ProductCategoryIdentity categories) {
        this.categories = categories;
        return this;
    }
}
