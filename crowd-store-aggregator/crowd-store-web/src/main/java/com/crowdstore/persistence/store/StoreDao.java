package com.crowdstore.persistence.store;

import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.users.UserIdentity;

import java.util.List;

/**
 * @author damienriccio
 */
public interface StoreDao {
    List<UserIdentity> getStoreUsers(String storeToken);
    void createStore(FlatStore store);
    void hardDeleteStoresByNames(String... storeNames);
}
