package com.crowdstore.service.store;

import com.crowdstore.models.users.UserIdentity;

import java.util.List;

/**
 * @author damienriccio
 */
public interface StoreService {
    List<UserIdentity> getStoreUsers(String storeToken);
}
