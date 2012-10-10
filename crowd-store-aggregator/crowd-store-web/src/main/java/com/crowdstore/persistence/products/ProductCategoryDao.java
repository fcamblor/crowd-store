package com.crowdstore.persistence.products;

import com.crowdstore.models.products.ProductCategoryIdentity;

/**
 * @author fcamblor
 */
public interface ProductCategoryDao {
    ProductCategoryIdentity create(ProductCategoryIdentity productCategory);
    void deleteByIds(Long... ids);
}
