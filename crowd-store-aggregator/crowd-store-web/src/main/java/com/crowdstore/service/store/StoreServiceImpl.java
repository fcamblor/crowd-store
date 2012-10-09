package com.crowdstore.service.store;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.persistence.store.StoreDao;
import com.crowdstore.service.security.PermissionsService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
}
