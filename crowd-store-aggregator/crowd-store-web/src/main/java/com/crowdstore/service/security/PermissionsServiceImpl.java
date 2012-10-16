package com.crowdstore.service.security;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.security.GlobalAuthorization;
import com.crowdstore.models.security.StoreAuthorization;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author fcamblor
 */
@Service
public class PermissionsServiceImpl implements PermissionsService {
    @InjectLogger
    Logger logger;

    @Inject
    AppContext appContext;

    @Override
    public boolean canBrowseStoreUsers(String storeName) {
        return currentUserBelongToStore(storeName);
    }

    @Override
    public boolean canCreateNewStore() {
        return appContext.getAuthenticatedUser().hasGlobalAuthorization(GlobalAuthorization.CREATE_STORE);
    }

    @Override
    public boolean canCreateNewUser() {
        return appContext.getAuthenticatedUser().hasGlobalAuthorization(GlobalAuthorization.CREATE_USER);
    }

    @Override
    public boolean canDeleteUsers() {
        return appContext.getAuthenticatedUser().hasGlobalAuthorization(GlobalAuthorization.DELETE_USER);
    }

    @Override
    public boolean canDeleteStores(String... storeNames) {
        return currentUserHasAuthorizationOnStores(StoreAuthorization.DELETE_STORE, storeNames);
    }

    @Override
    public boolean canRemoveUserFromStores(String... storeNames) {
        return currentUserHasAuthorizationOnStores(StoreAuthorization.REMOVE_USER_FROM_STORE, storeNames);
    }

    private boolean currentUserHasAuthorizationOnStores(StoreAuthorization storeAuthorization, String... storeNames){
        for(String storeName : storeNames){
            if(!appContext.getAuthenticatedUser().hasStoreAuthorization(storeName, storeAuthorization)){
                return false;
            }
        }
        return true;
    }

    private boolean currentUserBelongToStore(String storeName) {
        return appContext.getAuthenticatedUser().belongsToStore(storeName);
    }
}
