package com.crowdstore.service.store;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.products.AvailableProduct;
import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.persistence.store.StoreDao;
import com.crowdstore.service.security.PermissionsService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static com.crowdstore.common.security.Permissions.ensure;

/**
 * @author damienriccio
 */
@Service
public class StoreServiceImpl implements StoreService {
    @InjectLogger
    private Logger LOGGER;

    @Inject
    PermissionsService permissionsService;

    @Inject
    StoreDao storeDao;

    @Override
    public List<UserIdentity> getStoreUsers(String storeToken) {
        ensure(permissionsService.canBrowseStoreUsers(storeToken), "Cannot browse store [%s]'s users", storeToken);

        return storeDao.getStoreUsers(storeToken);
    }

    @Override
    public void createStore(FlatStore store) {
        ensure(permissionsService.canCreateNewStore(), "Cannot create a new store");

        storeDao.createStore(store);
    }

    @Override
    public void hardDeleteStoresByNames(String... storeNames) {
        ensure(permissionsService.canDeleteStores(storeNames), "Cannot delete at least 1 store among stores : %s", Arrays.toString(storeNames));

        storeDao.hardDeleteStoresByNames(storeNames);
    }

    @Override
    public void attachUserToStores(Long userId, StoreRole storeRole, String... storeNames) {
        ensure(permissionsService.canRemoveUserFromStores(storeNames), "Cannot remove user %s from stores : %s", userId, Arrays.toString(storeNames));

        for(String storeName : storeNames){
            storeDao.attachUserToStore(userId, storeName, storeRole);
        }
    }

    @Override
    public List<AvailableProduct> getStoreAvailableProducts(String storeToken) {
      //  ensure(permissionsService.canBrowseStoreAvailableProducts(storeToken), "Cannot browse store [%s]'s available products", storeToken);

        return storeDao.getStoreAvailableProducts(storeToken);
    }

}
