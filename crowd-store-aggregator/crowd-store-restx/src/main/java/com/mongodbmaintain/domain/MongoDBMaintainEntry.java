package com.mongodbmaintain.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @author fcamblor
 */
public class MongoDBMaintainEntry {
    final BigDecimal id;
    final DateTime executedAt;
    final boolean succeeded;

    protected MongoDBMaintainEntry(BigDecimal id, DateTime executedAt, boolean succeeded) {
        this.id = id;
        this.executedAt = executedAt;
        this.succeeded = succeeded;
    }

    @JsonCreator
    public static MongoDBMaintainEntry createEntry(@JsonProperty("id") BigDecimal id,
                                                   @JsonProperty("executedAt") DateTime executedAt,
                                                   @JsonProperty("succeeded") boolean succeeded){
        return new MongoDBMaintainEntry(id, executedAt, succeeded);
    }

    public BigDecimal getId() {
        return id;
    }

    public DateTime getExecutedAt() {
        return executedAt;
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
