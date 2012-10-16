package com.crowdstore.persistence.store;

import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author damienriccio
 */
@Repository
public class StoreDaoImpl extends SqlSessionDaoSupport implements StoreDao {
    @Override
    public List<UserIdentity> getStoreUsers(String storeToken) {
        return DaoSupport.selectList(this, "getStoreUsers", storeToken);
    }

    @Override
    public void createStore(FlatStore store) {
        DaoSupport.insert(this, "createStore", store);
    }

    @Override
    public void hardDeleteStoresByNames(String... storeNames) {
        DaoSupport.delete(this, "deleteStoresByNames", storeNames);
    }

    @Override
    public void attachUserToStore(Long userId, String storeName, StoreRole storeRole) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("storeName", storeName);
        params.put("role", storeRole.name());
        DaoSupport.insert(this, "attachUserToStore", params);
    }
}
