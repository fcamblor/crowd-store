package com.mongodbmaintain;

import com.google.common.base.Optional;
import com.mongodbmaintain.domain.MongoDBMaintainEntry;
import com.mongodbmaintain.persistence.MongoDBMaintainPersistence;
import org.joda.time.DateTime;
import restx.factory.Factory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fcamblor
 */
public class MongoDBMaintainerServlet extends HttpServlet {
    public static abstract class Step implements Comparable<Step> {
        final BigDecimal id;

        public Step(BigDecimal id) {
            this.id = id;
        }

        protected abstract void process();

        @Override
        public int compareTo(Step o) {
            return this.id.compareTo(o.id);
        }
    }

    public MongoDBMaintainerServlet() {
    }

    @Override
    public void init() throws ServletException {
        Factory factory = Factory.builder().addFromServiceLoader().build();
        List<Step> steps = new ArrayList<>(factory.queryByClass(Step.class).findAsComponents());

        MongoDBMaintainPersistence mongoDBMaintainPersistence = factory.queryByClass(MongoDBMaintainPersistence.class).findOne().get().getComponent();

        // First, sorting available steps
        Collections.sort(steps);

        Optional<MongoDBMaintainEntry> latestSucceededMongoDBMaintainEntry = mongoDBMaintainPersistence.findLatestSucceededMongoDBMaintainEntry();
        BigDecimal greatesIndex = latestSucceededMongoDBMaintainEntry.isPresent()?latestSucceededMongoDBMaintainEntry.get().getId():BigDecimal.ZERO;
        for(Step step : steps){
            if(greatesIndex.compareTo(step.id) < 0){
                try {
                    step.process();
                    mongoDBMaintainPersistence.saveEntry(MongoDBMaintainEntry.createEntry(step.id, DateTime.now(), true));
                } catch (Exception e){
                    mongoDBMaintainPersistence.saveEntry(MongoDBMaintainEntry.createEntry(step.id, DateTime.now(), false));
                    // Re-throwing exception
                    throw e;
                }
            }
        }
    }
}
