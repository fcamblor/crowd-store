package com.crowdstore.test.factories;

import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.store.StoreIdentity;
import com.crowdstore.restapi.common.PermissionFaker;
import com.crowdstore.service.store.StoreService;
import com.crowdstore.test.factories.common.EntitiesTestFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fcamblor
 */
@Component
public class StoreTestFactory extends EntitiesTestFactory {

    @Inject
    StoreService storeService;

    @Inject
    PermissionFaker permissionFaker;

    public FlatStore newPersistedFlatFlow(String storeName) {
        final FlatStore store = new FlatStore(new StoreIdentity(null).setName(storeName));
        permissionFaker.executeWithAllRights(new Runnable() {
            @Override
            public void run() {
                storeService.createStore(store);
            }
        });
        return store;
    }

    public void attachUserToStores(final Long userId, final StoreRole role, final String... attachedStoreNames) {
        permissionFaker.executeWithAllRights(new Runnable() {
            @Override
            public void run() {
                storeService.attachUserToStores(userId, role, attachedStoreNames);
            }
        });
    }

    public void cleanPersistedFlatStores(boolean rethrowException, FlatStore... createdFlatStores){
        cleanPersistedFlatStores(rethrowException, Arrays.asList(createdFlatStores));
    }

    public void cleanPersistedFlatStores(boolean rethrowException, final List<FlatStore> createdFlatStores) {
        super.cleanPersistedElements(rethrowException, createdFlatStores, new Runnable() {
                        @Override
                        public void run() {
                            List<String> storeNamesToDelete = new ArrayList<String>();
                            for(FlatStore store : createdFlatStores){
                                storeNamesToDelete.add(store.getIdentity().getName());
                            }
                            storeService.hardDeleteStoresByNames(storeNamesToDelete.toArray(new String[0]));
                        }
                    });
    }
}
