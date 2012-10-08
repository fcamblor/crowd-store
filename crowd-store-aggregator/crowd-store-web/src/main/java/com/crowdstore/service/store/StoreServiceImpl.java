package com.crowdstore.service.store;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.users.User;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.persistence.store.StoreDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author damienriccio
 */
@Service
public class StoreServiceImpl implements StoreService {
    @InjectLogger
    private Logger LOGGER;

    @Inject
    StoreDao storeDao;

    @Override
    public List<UserIdentity> getStoreUsers(String storeToken) {
        return storeDao.getStoreUsers(storeToken);
    }
}
