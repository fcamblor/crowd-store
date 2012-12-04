package com.crowdstore.persistence.sellingOrder;

import com.crowdstore.models.sellingOrder.FlatSellingOrder;

/**
 * @author damienriccio
 * @date 11/17/12
 */
public interface SellingOrderDao {
    FlatSellingOrder create(FlatSellingOrder flatSellingOrder);
}
