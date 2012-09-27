package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

import java.math.BigDecimal;

/**
 * @author damienriccio
 */
public class BuyingOrder extends GenericIdentifiable {
    private Integer quantity;
    private Product product;
    private BigDecimal price;

    public BuyingOrder() {
    }

    public BuyingOrder(Long id) {
        super(id);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuyingOrder that = (BuyingOrder) o;

        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quantity != null ? quantity.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
