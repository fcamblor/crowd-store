package com.crowdstore.persistence.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.FlatUser;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author damienriccio
 */
@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

    /**
     * Temporary class used to provide a workaround for a problem encountered
     * and described here : https://groups.google.com/forum/?fromgroups=#!topic/mybatis-user/myPouCEgF5c
     * Not really proud of this, but it doesn't seem we can do better...
     */
    public static class TmpAuthenticatedUser extends AuthenticatedUser {
        private List<UserStoreRole> storeRoles;

        protected TmpAuthenticatedUser(){
            this.storeRoles = new ArrayList<UserStoreRole>(){
                @Override
                public boolean add(UserStoreRole userStoreRole) {
                    boolean res = super.add(userStoreRole);
                    getStoresAuthorizations().put(userStoreRole.getStoreName(), userStoreRole.getRole().getAuthorizations());
                    return res;
                }

                @Override
                public void add(int index, UserStoreRole userStoreRole) {
                    super.add(index, userStoreRole);
                    getStoresAuthorizations().put(userStoreRole.getStoreName(), userStoreRole.getRole().getAuthorizations());
                }
            };
        }

        @Override
        public AuthenticatedUser setStoreRoles(List<UserStoreRole> storeRoles) {
            this.storeRoles.addAll(storeRoles);
            return super.setStoreRoles(storeRoles);
        }

        public List<UserStoreRole> getStoreRoles(){
            return this.storeRoles;
        }
    }

    @Override
    public AuthenticatedUser findAuthenticatedUserByCredentials(Credentials credentials) {
        TmpAuthenticatedUser authenticatedUser = DaoSupport.selectOne(this, "findAuthenticatedUserByCredentials", credentials);
        return authenticatedUser;
    }

    @Override
    public AuthenticatedUser findAuthenticatedUserByPrivateToken(String privateToken) {
        TmpAuthenticatedUser authenticatedUser = DaoSupport.selectOne(this, "findAuthenticatedUserByPrivateToken", privateToken);
        return authenticatedUser;
    }

    @Override
    public void createUser(FlatUser user) {
        DaoSupport.insert(this, "createUser", user);
    }

    @Override
    public void hardDeleteUsersByIds(Long... userIds) {
        DaoSupport.deleteByIds(this, "hardDeleteUsersByIds", userIds);
    }

    @Override
    public void detachStoresToUsers(Long[] userIds) {
        DaoSupport.delete(this, "detachStoresToUsers", userIds);
    }
}
