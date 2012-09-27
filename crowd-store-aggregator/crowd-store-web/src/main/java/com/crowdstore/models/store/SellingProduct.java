package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author damienriccio
 */
public class SellingProduct extends GenericIdentifiable {
    private Product product;
    private BigDecimal price;
    private Date endDate;

    public SellingProduct() {

    }

    public SellingProduct(Long id) {
        super(id);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellingProduct that = (SellingProduct) o;

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
