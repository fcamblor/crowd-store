package com.crowdstore.test.factories.common;

import com.crowdstore.restapi.common.PermissionFaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fcamblor
 */
public class EntitiesTestFactory {
    Logger LOG = LoggerFactory.getLogger(EntitiesTestFactory.class);

    @Inject
    protected PermissionFaker permissionFaker;

    private List<RuntimeException> exceptionsDuringClean = new ArrayList<RuntimeException>();

    protected void cleanPersistedElements(boolean rethrowException, List<?> entitiesToClean, Runnable runnable) {

        if(entitiesToClean == null || entitiesToClean.size() == 0){
            return;
        }

        try {
            permissionFaker.executeWithAllRights(runnable);
        }catch(RuntimeException t){
            if(rethrowException){
                throw t;
            } else {
                exceptionsDuringClean.add(t);
                LOG.error("Error while deleting entities in "+this.getClass().getName(), t);
            }
        }
    }

    public void ensurePersistedElementsCleaningWasMadeCorrectly(){
        if(exceptionsDuringClean.size() != 0){
            StringWriter strWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(strWriter);
            writer.write("Clean of " + this.getClass().getName() + " failed : ");
            for(RuntimeException runtimeException : exceptionsDuringClean){
                writer.write(runtimeException.getMessage() + " : ");
                runtimeException.printStackTrace(writer);
                writer.write("\n");
            }

            String errMsg = strWriter.toString();

            writer.close();

            throw new AssertionError(errMsg);
        }
    }
}
