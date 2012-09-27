package com.crowdstore.models.store;

import com.crowdstore.models.common.GenericIdentifiable;

import java.util.Date;

/**
 * @author damienriccio
 */
public class SellingOrder extends GenericIdentifiable {
    private int quantity;
    private SellingProduct sellingProduct;
    private Date orderDate;
    private SellingOrderStatus status;

    public SellingOrder() {
    }

    public SellingOrder(Long id) {
        super(id);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SellingProduct getSellingProduct() {
        return sellingProduct;
    }

    public void setSellingProduct(SellingProduct sellingProduct) {
        this.sellingProduct = sellingProduct;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public SellingOrderStatus getStatus() {
        return status;
    }

    public void setStatus(SellingOrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellingOrder that = (SellingOrder) o;

        if (quantity != that.quantity) return false;
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;
        if (sellingProduct != null ? !sellingProduct.equals(that.sellingProduct) : that.sellingProduct != null)
            return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quantity;
        result = 31 * result + (sellingProduct != null ? sellingProduct.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
