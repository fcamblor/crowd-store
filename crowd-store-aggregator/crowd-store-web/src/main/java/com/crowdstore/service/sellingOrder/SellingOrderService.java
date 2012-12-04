package com.crowdstore.service.sellingOrder;

import com.crowdstore.models.sellingOrder.FlatSellingOrder;

/**
 * @author damienriccio
 * @date 11/17/12
 */
public interface SellingOrderService {
    void create(FlatSellingOrder flatSellingOrder);
}
