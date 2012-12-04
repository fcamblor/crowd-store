package com.crowdstore.models.sellingOrder;

import com.crowdstore.models.common.GenericIdentifiable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * @author damienriccio
 * @date 11/17/12
 */
public class SellingOrderIdentity extends GenericIdentifiable {
    private Integer quantity;
    private Date orderDate;
    private SellingOrderStatus status;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @JsonProperty
    public SellingOrderStatus getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(SellingOrderStatus status) {
        this.status = status;
    }
}
