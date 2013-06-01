package com.crowdstore;

import com.google.common.base.Charsets;
import restx.SignatureKey;
import restx.factory.Module;
import restx.factory.Provides;
import restx.jongo.JongoFactory;

import javax.inject.Named;

@Module
public class AppModule {
    @Provides
    public SignatureKey signatureKey() {
         return new SignatureKey("-7001706471607077523 crowd-store crowd-store 99dab1c6-4a86-4c01-9fa6-2caa7d9bb9d0".getBytes(Charsets.UTF_8));
    }

    @Provides @Named(JongoFactory.JONGO_DB_NAME)
    public String jongoDbName(){
        return "crowd-store";
    }
}
