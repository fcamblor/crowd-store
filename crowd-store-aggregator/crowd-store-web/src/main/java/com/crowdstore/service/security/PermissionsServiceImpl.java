package com.crowdstore.service.security;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.context.AppContext;
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

    private boolean currentUserBelongToStore(String storeName) {
        return appContext.getAuthenticatedUser().belongsToStore(storeName);
    }
}
