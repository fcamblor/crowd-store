package com.crowdstore;

import com.crowdstore.domain.users.User;
import com.crowdstore.persistence.UserPersistence;
import com.google.common.base.Charsets;
import com.google.common.cache.CacheLoader;
import restx.RestxSession;
import restx.SignatureKey;
import restx.factory.Module;
import restx.factory.Provides;
import restx.jongo.JongoFactory;
import restx.security.RestxPrincipal;

import javax.inject.Named;

@Module
public class AppModule {
    @Provides
    public RestxSession.Definition.Entry usersSessionEntry(final UserPersistence userPersistence) {
        return new RestxSession.Definition.Entry(User.class, RestxPrincipal.SESSION_DEF_KEY, new CacheLoader<String, User>() {
            @Override
            public User load(String key) throws Exception {
                return userPersistence.findUserByKey(key).orNull();
            }
        });
    }

    @Provides
    public SignatureKey signatureKey() {
         return new SignatureKey("-7001706471607077523 crowd-store crowd-store 99dab1c6-4a86-4c01-9fa6-2caa7d9bb9d0".getBytes(Charsets.UTF_8));
    }

    @Provides @Named(JongoFactory.JONGO_DB_NAME)
    public String jongoDbName(){
        return "crowd-store";
    }
}
