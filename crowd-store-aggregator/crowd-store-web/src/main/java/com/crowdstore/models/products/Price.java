package com.crowdstore.models.products;

import java.math.BigDecimal;

/**
 * @author damienriccio
 */
public class Price {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public Price setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
