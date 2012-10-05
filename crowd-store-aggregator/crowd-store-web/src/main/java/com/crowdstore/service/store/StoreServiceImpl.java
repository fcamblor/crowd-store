package com.crowdstore.service.store;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.models.store.Store;
import com.crowdstore.persistence.store.StoreDao;
import com.crowdstore.service.user.UserService;
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

    @Inject
    UserService userService;

    @Override
    public List<Store> getAllStores() {
        return storeDao.getAllStores();
    }
}
