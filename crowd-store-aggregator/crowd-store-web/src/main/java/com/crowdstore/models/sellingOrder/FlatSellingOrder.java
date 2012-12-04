package com.crowdstore.models.sellingOrder;

import com.crowdstore.models.common.GenericIdentifiableContainer;

/**
 * @author damienriccio
 * @date 11/17/12
 */
public class FlatSellingOrder extends GenericIdentifiableContainer<SellingOrderIdentity> {
    private Long userId;
    private Long sellingProductId;

    protected FlatSellingOrder() { super(); }
    public FlatSellingOrder(SellingOrderIdentity identity){
        super(identity);
    }

    @Override
    public SellingOrderIdentity getIdentity() {
        return identity;
    }

    @Override
    public void setIdentity(SellingOrderIdentity identity) {
        this.identity = identity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellingProductId() {
        return sellingProductId;
    }

    public void setSellingProductId(Long sellingProductId) {
        this.sellingProductId = sellingProductId;
    }
}
