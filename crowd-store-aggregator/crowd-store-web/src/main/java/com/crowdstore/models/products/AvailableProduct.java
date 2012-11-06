package com.crowdstore.models.products;

import com.crowdstore.models.common.GenericIdentifiable;

import javax.validation.constraints.NotNull;

/**
 * @author damienriccio
 */
public class AvailableProduct extends GenericIdentifiable {
    @NotNull
    private Product product;
    private Price unitSellingPrice;

    public AvailableProduct(){ super(null); }
    public AvailableProduct(Long id){ super(id); }

    public Product getProduct() {
        return product;
    }

    public AvailableProduct setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Price getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public AvailableProduct setUnitSellingPrice(Price unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
        return this;
    }
}
