package com.crowdstore.persistence.common;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * This class was strongly inspired from class given in this issue http://code.google.com/p/mybatis/issues/detail?id=679
 *
 * Some MyBatis configuration couldn't be specified in Spring's context file.
 * MyBatis is used mybatis-config.xml file for it. Therefore has been
 * implemented this class that allows to specify almost all parameters in
 * Spring's XML file.
 *
 * @author Emin Mamedov
 *
 */
public class MybatisSessionFactoryBean extends SqlSessionFactoryBean {
    private static final Logger log = LoggerFactory.getLogger(MybatisSessionFactoryBean.class);

    /**
     * Builds session factory and configures it using properties specified in
     * Spring's context file
     *
     * @return created sql session factory
     * @throws IOException
     *             if loading the config file failed
     *
     * @see org.mybatis.spring.SqlSessionFactoryBean#buildSqlSessionFactory()
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        SqlSessionFactory sessionFactory = super.buildSqlSessionFactory();
        Configuration configuration = sessionFactory.getConfiguration();
        Properties props = new Properties();
        try {
            final Field configProperty = SqlSessionFactoryBean.class.getDeclaredField("configurationProperties");
            configProperty.setAccessible(true);
            props = (Properties) configProperty.get(this);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(defaultIfBlank(
                props.getProperty("autoMappingBehavior"), "PARTIAL")));
        configuration.setCacheEnabled(booleanValueOf(props.getProperty("cacheEnabled"), true));
        configuration.setLazyLoadingEnabled(booleanValueOf(props.getProperty("lazyLoadingEnabled"), false));
        configuration.setAggressiveLazyLoading(booleanValueOf(props.getProperty("aggressiveLazyLoading"), true));
        configuration
                .setMultipleResultSetsEnabled(booleanValueOf(props.getProperty("multipleResultSetsEnabled"), true));
        configuration.setUseColumnLabel(booleanValueOf(props.getProperty("useColumnLabel"), true));
        configuration.setUseGeneratedKeys(booleanValueOf(props.getProperty("useGeneratedKeys"), false));
        configuration.setDefaultExecutorType(ExecutorType.valueOf(defaultIfBlank(
                props.getProperty("defaultExecutorType"), "SIMPLE")));
        configuration.setDefaultStatementTimeout(Integer.parseInt(defaultIfBlank(props.getProperty("defaultStatementTimeout"), "0")));
        configuration.setMapUnderscoreToCamelCase(booleanValueOf(props.getProperty("mapUnderscoreToCamelCase"), false));
        configuration.setSafeRowBoundsEnabled(booleanValueOf(props.getProperty("safeRowBoundsEnabled"), true));

        return sessionFactory;
    }

    /**
     * Converts string value to Boolean or returns default value if it is null
     *
     * @param value
     *            value that should be converted
     * @param defaultValue
     *            default value
     * @return converted value or default value if value is null
     */
    protected Boolean booleanValueOf(String value, Boolean defaultValue) {
        return value == null ? defaultValue : Boolean.valueOf(value);
    }

    protected <T> T defaultIfBlank(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

}
