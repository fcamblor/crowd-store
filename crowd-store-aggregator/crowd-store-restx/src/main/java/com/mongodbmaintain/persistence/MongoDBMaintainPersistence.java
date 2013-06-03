package com.mongodbmaintain.persistence;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.mongodbmaintain.domain.MongoDBMaintainEntry;
import org.jongo.MongoCollection;
import restx.factory.Component;
import restx.jongo.JongoCollection;

import javax.inject.Named;

/**
 * @author fcamblor
 */
@Component
public class MongoDBMaintainPersistence {

    private final JongoCollection mongoDbMaintainEntries;

    public MongoDBMaintainPersistence(@Named("mongoDbMaintainEntries") JongoCollection mongoDbMaintainEntries) {
        this.mongoDbMaintainEntries = mongoDbMaintainEntries;
    }

    private MongoCollection mongoDbMaintainEntries() {
        return mongoDbMaintainEntries.get();
    }

    public MongoDBMaintainEntry saveEntry(MongoDBMaintainEntry entry){
        mongoDbMaintainEntries().save(entry);
        return entry;
    }
    
    public Optional<MongoDBMaintainEntry> findLatestSucceededMongoDBMaintainEntry(){
        return Optional.fromNullable(Iterables.getFirst(
                mongoDbMaintainEntries().find("{succeeded:true}").sort("{id:-1}").limit(1).as(MongoDBMaintainEntry.class), null
        ));
    }

}
