package com.crowdstore.service.security;

/**
 * @author fcamblor
 * Permissions oriented service
 * Every public service should be feature-oriented
 */
public interface PermissionsService {
    boolean canBrowseStoreUsers(String storeName);
    boolean canCreateNewStore();
    boolean canDeleteStores(String... storeNames);
}
