package com.crowdstore.test.rules;

import com.crowdstore.common.threads.AppThreadedInfos;
import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.test.factories.StoreTestFactory;
import org.junit.rules.ExternalResource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fcamblor
 * JUnit @Rule used as a facade to create different test entities in unit tests
 * Every created entities will be cleaned in the end of the test
 */
@Component
@Scope("prototype")
public class ModelsTestFactory extends ExternalResource {

    @Inject
    StoreTestFactory storeTestFactory;

    @Inject
    AppContext appContext;

    @Inject
    MessageSource messageSource;

    List<FlatStore> createdFlatStores = new ArrayList<>();

    @Override
    protected void before() throws Throwable {
        super.before();

        // TODO: Is it really appropriate to provide threaded context here ?
        // Shouldn't it be provided earlier ?
        AppThreadedInfos.provideAppThreadedContextToCurrentThread(AppThreadedInfos.Context.create(
                appContext, messageSource
        ));

        for(List entitiesList : entitiesLists()){
            if(!entitiesList.isEmpty()){
                fail("Previous entitiesList has not been flushed correctly !");
            }
        }
    }

    @Override
    protected void after() {
        // Cleaning everything.. in the correct order
        storeTestFactory.cleanPersistedFlatStores(false, createdFlatStores);

        for(List entitiesList : entitiesLists()){
            entitiesList.clear();
        }

        storeTestFactory.ensurePersistedElementsCleaningWasMadeCorrectly();

        super.after();
    }

    protected List[] entitiesLists(){
        return new List[]{ createdFlatStores };
    }

    public FlatStore newPersistedFlatFLow(String storeName){
        FlatStore store = storeTestFactory.newPersistedFlatFlow(storeName);
        createdFlatStores.add(store);
        return store;
    }

    public List<FlatStore> getCreatedFlatStores() {
        return createdFlatStores;
    }
}
