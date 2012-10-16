package com.crowdstore.service.security;

/**
 * @author fcamblor
 * Permissions oriented service
 * Every public service should be feature-oriented
 */
public interface PermissionsService {
    boolean canCreateNewStore();
    boolean canCreateNewUser();
    boolean canDeleteUsers();

    boolean canBrowseStoreUsers(String storeName);
    boolean canDeleteStores(String... storeNames);
    boolean canRemoveUserFromStores(String... storeNames);
}
