package com.crowdstore.persistence.associations;

import com.crowdstore.common.utils.Lists;

import java.util.List;
import java.util.Map;

/**
 * @author fcamblor
 */
public interface AssociationDao {

    /**
     * Descriptor class is intended to describe your Association mapping used when calling
     * create/update/delete/get operations
     */
    public static class Descriptor {
        private String associationTableName;
        private String foreignTableName;
        private String col1Name;
        private String col2Name;
        private String idColumnName;
        private String codeColumnName;
        private String labelColumnName;

        public Descriptor(String associationTableName, String foreignTableName, String col1Name, String col2Name,
                          String idColumnName, String codeColumnName, String labelColumnName) {
            this.associationTableName = associationTableName;
            this.foreignTableName = foreignTableName;
            this.col1Name = col1Name;
            this.col2Name = col2Name;
            this.idColumnName = idColumnName;
            this.codeColumnName = codeColumnName;
            this.labelColumnName = labelColumnName;
        }

        public Descriptor(String associationTableName, String foreignTableName, String col1Name, String col2Name) {
            this(associationTableName, foreignTableName, col1Name, col2Name, "id", "code", "label");
        }

        public String getAssociationTableName() {
            return associationTableName;
        }

        public String getForeignTableName() {
            return foreignTableName;
        }

        public String getCol1Name() {
            return col1Name;
        }

        public String getCol2Name() {
            return col2Name;
        }

        public String getIdColumnName() {
            return idColumnName;
        }

        public String getCodeColumnName() {
            return codeColumnName;
        }

        public String getLabelColumnName() {
            return labelColumnName;
        }
    }

    List<Long> getAssociationKeys(Descriptor desc, Long col1Value);

    List<String> getAssociationStr(Descriptor desc, Long col1Value);

    List<Long> getAssociationKeys(Descriptor desc, Long[] col1Values);

    List<String> getAssociationStr(Descriptor desc, Long[] col1Values);

    <T> List<T> getAssociationAdditionnalColValuesLong(Descriptor descriptor, Long[] communityIds, String additionnalColumnName);

    /**
     * @param additionnalColumnValues Map which is considered as :
     *                                [ "column name" => value that will be persisted for additionnal column ]
     */
    void insertAssociationValue(Descriptor desc, Long col1Value, Object col2Value, Map<String, ?> additionnalColumnValues);

    void insertAssociationValue(Descriptor desc, Long col1Value, Object col2Value);

    /**
     * @param additionnalFielValues Map which is considered as :
     *                              [ "column name" => [ col2Value in valuesToSet List => value that will be persisted for additionnal column ] ]
     */
    Lists.Diff<String> updateAssociationValuesStr(Descriptor desc, Long col1Value, List<String> valuesToSet, Map<String, Map<String, ?>> additionnalFielValues);

    Lists.Diff<String> updateAssociationValuesStr(Descriptor desc, Long col1Value, List<String> valuesToSet);

    /**
     * @param additionnalFielValues Map which is considered as :
     *                              [ "column name" => [ col2Value in valuesToSet List => value that will be persisted for additionnal column ] ]
     */
    Lists.Diff<Long> updateAssociationValuesLong(Descriptor desc, Long col1Value, List<Long> valuesToSet, Map<String, Map<Long, ?>> additionnalFielValues);

    Lists.Diff<Long> updateAssociationValuesLong(Descriptor desc, Long col1Value, List<Long> valuesToSet);

    void updateAssociationValue(Descriptor desc, Long col1Value, Object col2Value, Map<String, ?> additionnalColumns);

    int deleteAssociation(Descriptor desc, Long col1Value, Object col2Value);

    int deleteAssociationsByCol1Ids(Descriptor desc, Long[] col1Values);
}
