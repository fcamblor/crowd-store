package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

/**
 * @author damienriccio
 */
public class ProductCategory extends GenericIdentifiable {
    private String nameKey;
    private String descriptionKey;

    public ProductCategory() {
    }

    public ProductCategory(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategory that = (ProductCategory) o;

        if (descriptionKey != null ? !descriptionKey.equals(that.descriptionKey) : that.descriptionKey != null)
            return false;
        if (nameKey != null ? !nameKey.equals(that.nameKey) : that.nameKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nameKey != null ? nameKey.hashCode() : 0;
        result = 31 * result + (descriptionKey != null ? descriptionKey.hashCode() : 0);
        return result;
    }
}
