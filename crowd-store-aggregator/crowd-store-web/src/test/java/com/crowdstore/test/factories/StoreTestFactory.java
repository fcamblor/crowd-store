package com.crowdstore.test.factories;

import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.store.StoreIdentity;
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

    public FlatStore newPersistedFlatFlow(String storeName) {
        FlatStore store = new FlatStore(new StoreIdentity(null).setName(storeName));
        storeService.createStore(store);
        return store;
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
