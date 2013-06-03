package com.mongodbmaintain.steps;

import com.crowdstore.domain.users.User;
import com.crowdstore.domain.users.UserProfile;
import com.crowdstore.persistence.UserPersistence;
import com.mongodbmaintain.MongoDBMaintainerServlet;
import restx.factory.Component;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author fcamblor
 */
@Component
public class S001_CreateUsers extends MongoDBMaintainerServlet.Step {

    UserPersistence userPersistence;

    public S001_CreateUsers(UserPersistence userPersistence) {
        super(new BigDecimal("1"));
        this.userPersistence = userPersistence;
    }

    @Override
    protected void process() {
        this.userPersistence.saveUser(User.createUser(null, "Admin", "Admin", "admin@crowdstore.fr",
                "test",
                Arrays.asList(
                        UserProfile.GLOBAL_ADMIN.getProfileName()
                )
        ));
    }
}
