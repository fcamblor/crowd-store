package com.crowdstore.persistence.store;

import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author damienriccio
 */
@Repository
public class StoreDaoImpl extends SqlSessionDaoSupport implements StoreDao {
    @Override
    public List<UserIdentity> getStoreUsers(String storeToken) {
        return DaoSupport.selectList(this, "getStoreUsers", storeToken);
    }
}
