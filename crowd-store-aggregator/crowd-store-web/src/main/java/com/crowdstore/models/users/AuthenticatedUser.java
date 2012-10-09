package com.crowdstore.models.users;

import com.crowdstore.models.role.GlobalRole;
import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.security.GlobalAuthorization;
import com.crowdstore.models.security.StoreAuthorization;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author fcamblor
 */
public class AuthenticatedUser extends User {

    private Locale locale;
    private GlobalRole globalRole;
    private Map<String, StoreRole> storeRoles;

    public AuthenticatedUser(UserIdentity identity) {
        super(identity);
    }

    /**
     * @deprecated Use either LocaleContextHolder#getLocale() or AppThreadedInfos#getLocale() instead !
     *             This method should only be called when updating current locale in spring !
     *             Note : We cannot set an "accessibleLocale" flag+check here because we don't want to throw exception
     *             when serializing (in JSON) current user's locale. So the @deprecated flag is the better solution to avoid calling
     *             this method directly in code.
     */
    @Deprecated
    public Locale getLocale() {
        return locale;
    }

    public AuthenticatedUser setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public AuthenticatedUser setGlobalRole(GlobalRole _globalRole) {
        this.globalRole = _globalRole;
        return this;
    }

    public void setStoreRoles(Map<String, StoreRole> storeRoles) {
        this.storeRoles = storeRoles;
    }

    public Map<String, List<StoreAuthorization>> getStoresAuthorizations(){
        return Maps.transformValues(this.storeRoles, StoreRole.TO_STORE_AUTHORIZATIONS);
    }

    public List<StoreAuthorization> getStoreAuthorization(String storeName){
        StoreRole storeRole = this.storeRoles.get(storeName);
        if(storeRole==null){
            return null;
        }

        return storeRole.getAuthorizations();
    }

    public List<GlobalAuthorization> getGlobalAuthorizations() {
        return this.globalRole.getAuthorizations();
    }

    public boolean hasGlobalAuthorization(GlobalAuthorization authorization) {
        return globalRole.getAuthorizations().contains(authorization);
    }

    public boolean belongsToStore(String storeName) {
        return storeRoles.containsKey(storeName);
    }
}
