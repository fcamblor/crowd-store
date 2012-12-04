package com.crowdstore.persistence.sellingOrder;

import com.crowdstore.models.sellingOrder.FlatSellingOrder;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author damienriccio
 * @date 11/17/12
 */
@Repository
public class SellingOrderDaoImpl extends SqlSessionDaoSupport implements SellingOrderDao {
    @Override
    public FlatSellingOrder create(FlatSellingOrder flatSellingOrder) {
        DaoSupport.insert(this, "create", flatSellingOrder);
        return flatSellingOrder;
    }
}
