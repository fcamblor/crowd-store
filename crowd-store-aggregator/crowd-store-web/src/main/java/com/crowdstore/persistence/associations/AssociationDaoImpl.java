package com.crowdstore.persistence.associations;

import com.crowdstore.common.annotations.InjectLogger;
import com.crowdstore.common.utils.Lists;
import com.crowdstore.persistence.common.DaoSupport;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fcamblor
 */
@Repository
public class AssociationDaoImpl extends SqlSessionDaoSupport implements AssociationDao {
    @InjectLogger
    private Logger LOG;

    @Override
    public List<Long> getAssociationKeys(Descriptor desc, Long col1Value) {
        return getAssociationKeys(desc, new Long[]{col1Value});
    }

    @Override
    public List<String> getAssociationStr(Descriptor desc, Long col1Value) {
        return getAssociationStr(desc, new Long[]{col1Value});
    }

    @Override
    public List<Long> getAssociationKeys(Descriptor desc, Long[] col1Values) {
        Map<String, Object> params = new HashMap();
        params.put("col1Name", desc.getCol1Name());
        params.put("col2Name", desc.getCol2Name());
        params.put("assocationTable", desc.getAssociationTableName());
        params.put("col1Values", col1Values);
        try {
            return DaoSupport.selectList(this, "getRestrictionAssociationKeys", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during getRestrictionAssociationKeys, Context : " + params.toString());
            throw ex;
        }
    }

    @Override
    public List<String> getAssociationStr(Descriptor desc, Long[] col1Values) {
        Map<String, Object> params = new HashMap();
        params.put("col1Name", desc.getCol1Name());
        params.put("col2Name", desc.getCol2Name());
        params.put("assocationTable", desc.getAssociationTableName());
        params.put("col1Values", col1Values);
        try {
            return DaoSupport.selectList(this, "getRestrictionAssociationStr", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during getRestrictionAssociationStr, Context : " + params.toString());
            throw ex;
        }
    }

    @Override
    public List<Long> getAssociationAdditionnalColValuesLong(Descriptor desc, Long[] col1Values, String additionnalColumnName) {
        Map<String, Object> params = new HashMap();
        params.put("col1Name", desc.getCol1Name());
        params.put("assocationTable", desc.getAssociationTableName());
        params.put("col1Values", col1Values);
        params.put("additionnalColumnName", additionnalColumnName);
        try {
            return DaoSupport.selectList(this, "getAssociationAdditionnalColValuesStr", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during getAssociationAdditionnalColValuesStr, Context : " + params.toString());
            throw ex;
        }
    }

    @Override
    public void insertAssociationValue(Descriptor desc, Long col1Value, Object col2Value) {
        this.insertAssociationValue(desc, col1Value, col2Value, new HashMap<String, Object>());
    }

    @Override
    public void insertAssociationValue(Descriptor desc, Long col1Value, Object col2Value, Map<String, ?> additionnalColumns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("col1Value", col1Value);
        params.put("col2Value", col2Value);
        params.put("col1Name", desc.getCol1Name());
        params.put("col2Name", desc.getCol2Name());
        params.put("assocationTable", desc.getAssociationTableName());

        // Not using keySet() and valueSet() here since we want to keep key-value at the same lists' indexes
        List<String> additionnalColumnNames = new ArrayList<String>();
        List<Object> additionnalColumnValues = new ArrayList<Object>();
        for (Map.Entry<String, ?> en : additionnalColumns.entrySet()) {
            additionnalColumnNames.add(en.getKey());
            additionnalColumnValues.add(en.getValue());
        }
        params.put("additionnalColumnNames", additionnalColumnNames);
        params.put("additionnalColumnValues", additionnalColumnValues);
        try {
            DaoSupport.insert(this, "insertAssocValue", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during insertAssociationValue, Context : " + params.toString());
            throw ex;
        }
    }

    @Override
    public Lists.Diff<String> updateAssociationValuesStr(Descriptor desc, Long col1Value, List<String> valuesToSet, Map<String, Map<String, ?>> additionnalFielValues) {
        List<String> existingValues = getAssociationStr(desc, col1Value);
        return updateAssociationMappings(desc, col1Value, valuesToSet, existingValues, additionnalFielValues);
    }

    @Override
    public Lists.Diff<String> updateAssociationValuesStr(Descriptor desc, Long col1Value, List<String> valuesToSet) {
        return this.updateAssociationValuesStr(desc, col1Value, valuesToSet, new HashMap<String, Map<String, ?>>());
    }

    @Override
    public Lists.Diff<Long> updateAssociationValuesLong(Descriptor desc, Long col1Value, List<Long> valuesToSet, Map<String, Map<Long, ?>> additionnalFielValues) {
        List<Long> existingValues = getAssociationKeys(desc, col1Value);
        return updateAssociationMappings(desc, col1Value, valuesToSet, existingValues, additionnalFielValues);
    }

    @Override
    public Lists.Diff<Long> updateAssociationValuesLong(Descriptor desc, Long col1Value, List<Long> valuesToSet) {
        return this.updateAssociationValuesLong(desc, col1Value, valuesToSet, new HashMap<String, Map<Long, ?>>());
    }

    @Override
    public int deleteAssociationsByCol1Ids(Descriptor desc, Long[] col1Values) {
        Map<String, Object> params = new HashMap();
        params.put("assocationTable", desc.getAssociationTableName());
        params.put("col1Name", desc.getCol1Name());
        params.put("col1Values", col1Values);
        try {
            return DaoSupport.delete(this, "deleteAssociationsByCol1Value", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during deleteAssociationsByCol1Ids, Context : " + params.toString());
            throw ex;
        }
    }

    @Override
    public int deleteAssociation(Descriptor desc, Long col1Value, Object col2Value) {
        Map<String, Object> params = new HashMap();
        params.put("assocationTable", desc.getAssociationTableName());
        params.put("col1Name", desc.getCol1Name());
        params.put("col1Value", col1Value);
        params.put("col2Name", desc.getCol2Name());
        params.put("col2Value", col2Value);
        try {
            return DaoSupport.delete(this, "deleteAssociation", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during deleteAssociation, Context : " + params.toString());
            throw ex;
        }
    }

    @Override
    public void updateAssociationValue(Descriptor desc, Long col1Value, Object col2Value, Map<String, ?> additionnalColumns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("col1Value", col1Value);
        params.put("col2Value", col2Value);
        params.put("col1Name", desc.getCol1Name());
        params.put("col2Name", desc.getCol2Name());
        params.put("assocationTable", desc.getAssociationTableName());
        params.put("additionnalColumns", additionnalColumns.entrySet());
        try {
            DaoSupport.insert(this, "updateAssocValue", params);
        } catch (RuntimeException ex) {
            LOG.error("Error during updateAssociationValue, Context : " + params.toString());
            throw ex;
        }
    }

    private <T> Lists.Diff<T> updateAssociationMappings(Descriptor desc, Long col1Value, List<T> valuesToSet, List<T> existingValues,
                                                        Map<String, Map<T, ?>> additionnalFieldValues) {
        Lists.Diff<T> keysDiff = Lists.createDiff(existingValues, valuesToSet);

        for (T key : keysDiff.elementsToAdd) {
            Map<String, Object> columnAdditionnalValues = toColumnAdditionnalValues(key, additionnalFieldValues);
            insertAssociationValue(desc, col1Value, key, columnAdditionnalValues);
        }
        if (!additionnalFieldValues.isEmpty()) {
            for (T key : keysDiff.intersectionElements) {
                Map<String, Object> columnAdditionnalValues = toColumnAdditionnalValues(key, additionnalFieldValues);
                updateAssociationValue(desc, col1Value, key, columnAdditionnalValues);
            }
        }
        for (T key : keysDiff.elementsToRemove) {
            deleteAssociation(desc, col1Value, key);
        }

        return keysDiff;
    }

    private static <T> Map<String, Object> toColumnAdditionnalValues(T key, Map<String, Map<T, ?>> additionnalFieldValues) {
        Map<String, Object> additionnalColumnValues = new HashMap<String, Object>();
        for (Map.Entry<String, Map<T, ?>> entry : additionnalFieldValues.entrySet()) {
            additionnalColumnValues.put(entry.getKey(), entry.getValue().get(key));
        }
        return additionnalColumnValues;
    }
}
