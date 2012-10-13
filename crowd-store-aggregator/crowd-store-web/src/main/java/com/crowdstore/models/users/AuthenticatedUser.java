package com.crowdstore.models.users;

import com.crowdstore.models.role.GlobalRole;
import com.crowdstore.models.role.StoreRole;
import com.crowdstore.models.security.GlobalAuthorization;
import com.crowdstore.models.security.StoreAuthorization;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author fcamblor
 */
public class AuthenticatedUser extends User {

    private Locale locale;
    private List<GlobalAuthorization> globalAuthorizations;
    private Map<String, List<StoreAuthorization>> storeRoles;

    // For MyBatis Map<> special mapping
    // Dunno if we could avoid this...
    public static class UserStoreRole {
        String storeName;
        StoreRole role;
        public UserStoreRole(String storeName, StoreRole role) {
            this.storeName = storeName;
            this.role = role;
        }
        public void setStoreName(String _storeName){
            this.storeName = _storeName;
        }
        public void setRole(StoreRole _role){
            this.role = _role;
        }
    }

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
        this.globalAuthorizations = _globalRole.getAuthorizations();
        return this;
    }

    public AuthenticatedUser setStoreRoles(List<UserStoreRole> storeRoles) {
        this.storeRoles = new HashMap<>();
        for(UserStoreRole storeRole : storeRoles){
            this.storeRoles.put(storeRole.storeName, storeRole.role.getAuthorizations());
        }
        return this;
    }

    public Map<String, List<StoreAuthorization>> getStoresAuthorizations(){
        return this.storeRoles;
    }

    public List<StoreAuthorization> getStoreAuthorizations(String storeName){
        return this.getStoresAuthorizations().get(storeName);
    }

    public boolean hasStoreAuthorization(String storeName, StoreAuthorization storeAuthorization) {
        return getStoreAuthorizations(storeName).contains(storeAuthorization);
    }

    public List<GlobalAuthorization> getGlobalAuthorizations() {
        return this.globalAuthorizations;
    }

    public boolean hasGlobalAuthorization(GlobalAuthorization authorization) {
        return this.getGlobalAuthorizations().contains(authorization);
    }

    public boolean belongsToStore(String storeName) {
        return storeRoles.containsKey(storeName);
    }
}
