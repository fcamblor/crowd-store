package com.crowdstore.service.store;

import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.users.UserIdentity;

import java.util.List;

/**
 * @author damienriccio
 */
public interface StoreService {
    List<UserIdentity> getStoreUsers(String storeToken);
    void createStore(FlatStore store);
    void hardDeleteStoresByNames(String... storeNames);
    void attachUserToStores(Long userId, StoreRole storeRole, String... storeNames);
}
