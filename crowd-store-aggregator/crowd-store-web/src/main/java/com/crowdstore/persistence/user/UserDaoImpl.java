package com.crowdstore.persistence.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author damienriccio
 */
@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
    @Override
    public AuthenticatedUser findAuthenticatedUserByCredentials(Credentials credentials) {
        return DaoSupport.selectOne(this, "findAuthenticatedUserByCredentials", credentials);
    }
}
