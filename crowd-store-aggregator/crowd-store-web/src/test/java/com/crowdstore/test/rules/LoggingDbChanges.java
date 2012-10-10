package com.crowdstore.test.rules;

import com.crowdstore.persistence.tables.TableInfosDao;
import com.crowdstore.service.tables.TableInfosService;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author fcamblor
 * Will allow to ensure test class declaring this JUnit rule will clean data created during the
 * test execution...
 * Usage of this class should be like the following :
 * <code>
 *     public class FooTest {
 *         @Rule
 *         @Inject
 *         public LoggingDbChanges dbChangesLogger = new LoggingDbChanges();
 *
 *         @Test
 *         public void foo(){ .... }
 *     }
 * </code>
 *
 * Note that this rule will be incompatible with a forked test execution, or it could if and only if
 * each test is executed inside an isolated db transaction
 */
@Component
@Scope("singleton")
public class LoggingDbChanges extends TestWatcher {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingDbChanges.class);

    @Inject
    TableInfosService tableInfosService;

    List<TableInfosDao.TableInfos> startingTableInfos;

    boolean failIfNotCleanedData;

    public LoggingDbChanges(){
        this(true);
    }

    public LoggingDbChanges(boolean failIfNotCleanedData){
        this.failIfNotCleanedData = failIfNotCleanedData;
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);

        // Registering table infos...
        startingTableInfos = tableInfosService.getTableInfos();
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);

        List<TableInfosDao.TableInfos> endingTableInfos = tableInfosService.getTableInfos();
        List<TableInfosDao.TableInfos> diffedTableInfos = diffTableInfos(startingTableInfos, endingTableInfos);

        writeReport(description, diffedTableInfos);
        if(!diffedTableInfos.isEmpty() && failIfNotCleanedData){
            throw new IllegalStateException("Identified non cleaned data ! See test report ! "+description.getMethodName());
        }
    }

    private void writeReport(Description description, List<TableInfosDao.TableInfos> diffedTableInfos) {
        LOG.info(
                String.format("%s.%s db changes :", description.getClassName(), description.getMethodName()));
        if(diffedTableInfos.isEmpty()){
            LOG.info("   => No db changes detected !");
        } else {
            for(TableInfosDao.TableInfos diffedTableInfo : diffedTableInfos){
                LOG.info(String.format("         %s : %s line(s) added !", diffedTableInfo.getTableName(), diffedTableInfo.getRowCount()));
            }
        }
    }

    private static List<TableInfosDao.TableInfos> diffTableInfos(List<TableInfosDao.TableInfos> startingTableInfos,
                                                                 List<TableInfosDao.TableInfos> endingTableInfos) {

        List<TableInfosDao.TableInfos> diffedTableInfos = new ArrayList<TableInfosDao.TableInfos>();

        assertThat(startingTableInfos.size(), is(equalTo(endingTableInfos.size())));

        for(int i=0; i<startingTableInfos.size(); i++){
            assertThat(startingTableInfos.get(i).getTableName(), is(equalTo(endingTableInfos.get(i).getTableName())));

            Integer diffedValue = endingTableInfos.get(i).getRowCount() - startingTableInfos.get(i).getRowCount();
            if(diffedValue.intValue() != 0){
                diffedTableInfos.add(new TableInfosDao.TableInfos().setTableName(startingTableInfos.get(i).getTableName()).setRowCount(diffedValue));
            }
        }

        return diffedTableInfos;
    }
}
