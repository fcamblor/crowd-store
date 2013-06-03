package com.crowdstore.persistence;

import com.crowdstore.domain.users.User;
import com.google.common.base.Optional;
import org.jongo.MongoCollection;
import restx.factory.Component;
import restx.jongo.JongoCollection;

import javax.inject.Named;

/**
 * @author fcamblor
 */
@Component
public class UserPersistence {
    private final JongoCollection users;

    public UserPersistence(@Named("users") JongoCollection users) {
        this.users = users;
    }

    private MongoCollection users() {
        return users.get();
    }

    public Optional<User> findUserByLoginAndPasswordHash(String email, String passwordHash){
        return Optional.fromNullable(users().findOne("{email: #, passwordHash: #}", email, passwordHash).as(User.class));
    }

    public User saveUser(User user){
        users().save(user);
        return user;
    }

    public Optional<User> findUserByKey(String key) {
        return Optional.fromNullable(users().findOne("{_id: #}", key).as(User.class));
    }
}
