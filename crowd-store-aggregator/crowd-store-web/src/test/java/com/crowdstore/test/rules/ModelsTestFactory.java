package com.crowdstore.test.rules;

import com.crowdstore.common.threads.AppThreadedInfos;
import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.role.GlobalRole;
import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.users.FlatUser;
import com.crowdstore.test.factories.StoreTestFactory;
import com.crowdstore.test.factories.UserTestFactory;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
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
public class ModelsTestFactory extends TestWatcher {

    @Inject
    StoreTestFactory storeTestFactory;

    @Inject
    UserTestFactory userTestFactory;

    @Inject
    AppContext appContext;

    @Inject
    MessageSource messageSource;

    List<FlatStore> createdFlatStores = new ArrayList<>();
    List<FlatUser> createdFlatUsers = new ArrayList<>();

    @Override
    protected void starting(Description description) {
        super.starting(description);

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
    protected void finished(Description description) {
        // Cleaning everything.. in the correct order
        userTestFactory.cleanPersistedFlatUsers(false, createdFlatUsers);
        storeTestFactory.cleanPersistedFlatStores(false, createdFlatStores);

        for(List entitiesList : entitiesLists()){
            entitiesList.clear();
        }

        userTestFactory.ensurePersistedElementsCleaningWasMadeCorrectly();
        storeTestFactory.ensurePersistedElementsCleaningWasMadeCorrectly();

        super.finished(description);
    }

    protected List[] entitiesLists(){
        return new List[]{ createdFlatStores, createdFlatUsers };
    }

    public FlatStore newPersistedFlatStore(String storeName){
        FlatStore store = storeTestFactory.newPersistedFlatFlow(storeName);
        createdFlatStores.add(store);
        return store;
    }

    public FlatUser newPersistedFlatUserAdmin(String userKey, String[] attachedStoreNames){
        return newPersistedFlatUser(userKey, GlobalRole.ADMIN, StoreRole.ADMIN, attachedStoreNames);
    }

    public FlatUser newPersistedFlatUser(String userKey, GlobalRole globalRole, StoreRole storeRole, String[] attachedStoreNames){
        FlatUser user = userTestFactory.newPersistedFlatUser(userKey, globalRole);
        createdFlatUsers.add(user);

        storeTestFactory.attachUserToStores(user.getIdentity().getId(), storeRole, attachedStoreNames);

        return user;
    }

    public List<FlatUser> getCreatedFlatUsers() {
        return createdFlatUsers;
    }

    public List<FlatStore> getCreatedFlatStores() {
        return createdFlatStores;
    }
}
