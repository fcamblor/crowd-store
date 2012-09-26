package com.crowdstore.persistence.common;

import org.apache.commons.dbcp.BasicDataSource;

import javax.annotation.PostConstruct;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author fcamblor
 *         This datasource impl will handle correctly UTF-8 encodings to database
 *         Note that passing these parameters to connection url is not enough !
 *         See http://blog.tremend.ro/2006/08/25/end-to-end-utf-8-encoding-usage-with-mysql-and-spring/
 */
public class UTF8Datasource extends BasicDataSource {

    @PostConstruct
    public void init() {
        addConnectionProperty("useUnicode", "true");
        addConnectionProperty("characterEncoding", "UTF-8");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
