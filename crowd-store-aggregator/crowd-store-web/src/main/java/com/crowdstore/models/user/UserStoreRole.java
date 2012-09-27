package com.crowdstore.models.user;

import com.crowdstore.models.common.GenericIdentifiable;
import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.store.Store;

/**
 * @author damienriccio
 */
public class UserStoreRole extends GenericIdentifiable {
    private Store store;
    private StoreRole storeRole;

    public UserStoreRole() {
    }

    public UserStoreRole(Long id) {
        super(id);
    }

    public UserStoreRole(Store store, StoreRole storeRole) {
        super();
        this.store = store;
        this.storeRole = storeRole;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public StoreRole getStoreRole() {
        return storeRole;
    }

    public void setStoreRole(StoreRole storeRole) {
        this.storeRole = storeRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStoreRole that = (UserStoreRole) o;

        if (store != null ? !store.equals(that.store) : that.store != null) return false;
        if (storeRole != that.storeRole) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = store != null ? store.hashCode() : 0;
        result = 31 * result + (storeRole != null ? storeRole.hashCode() : 0);
        return result;
    }
}
