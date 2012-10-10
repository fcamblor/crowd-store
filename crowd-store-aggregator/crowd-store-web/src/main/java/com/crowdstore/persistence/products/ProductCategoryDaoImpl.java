package com.crowdstore.persistence.products;

import com.crowdstore.models.products.ProductCategoryIdentity;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author fcamblor
 */
@Repository
public class ProductCategoryDaoImpl extends SqlSessionDaoSupport implements ProductCategoryDao {
    @Override
    public ProductCategoryIdentity create(ProductCategoryIdentity productCategory) {
        DaoSupport.insert(this, "create", productCategory);
        return productCategory;
    }

    @Override
    public void deleteByIds(Long... ids) {
        DaoSupport.deleteByIds(this, "deleteByIds", ids);
    }
}
