package com.crowdstore.persistence.store;

import com.crowdstore.models.store.Store;

import java.util.List;

/**
 * @author damienriccio
 */
public interface StoreDao {
    List<Store> getAllStores();
}
