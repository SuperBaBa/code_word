package org.jarvis.sqltask.driver;

import java.util.Locale;

/**
 * @author marcus
 * @date 2020/11/16-11:11
 */
public enum DatabaseDriverEnum {
    /**
     * Unknown type.
     */
    UNKNOWN(null, null),

    /**
     * Apache Derby.
     */
    DERBY("Apache Derby", "org.apache.derby.jdbc.EmbeddedDriver", "org.apache.derby.jdbc.EmbeddedXADataSource",
            "SELECT 1 FROM SYSIBM.SYSDUMMY1"),

    /**
     * H2.
     */
    H2("H2", "org.h2.Driver", "org.h2.jdbcx.JdbcDataSource", "SELECT 1"),

    /**
     * HyperSQL DataBase.
     */
    HSQLDB("HSQL Database Engine", "org.hsqldb.jdbc.JDBCDriver", "org.hsqldb.jdbc.pool.JDBCXADataSource",
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SYSTEM_USERS"),

    /**
     * SQL Lite.
     */
    SQLITE("SQLite", "org.sqlite.JDBC"),

    /**
     * MySQL.
     */
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlXADataSource", "/* ping */ SELECT 1"),

    /**
     * Maria DB.
     */
    MARIADB("MySQL", "org.mariadb.jdbc.Driver", "org.mariadb.jdbc.MariaDbDataSource", "SELECT 1") {
        @Override
        public String getId() {
            return "mysql";
        }
    },

    /**
     * Google App Engine.
     */
    GAE(null, "com.google.appengine.api.rdbms.AppEngineDriver"),

    /**
     * Oracle.
     */
    ORACLE("Oracle", "oracle.jdbc.OracleDriver", "oracle.jdbc.xa.client.OracleXADataSource",
            "SELECT 'Hello' from DUAL"),

    /**
     * Postgres.
     */
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver", "org.postgresql.xa.PGXADataSource", "SELECT 1"),

    /**
     * Amazon Redshift.
     *
     * @since 2.2.0
     */
    REDSHIFT("Amazon Redshift", "com.amazon.redshift.jdbc.Driver", null, "SELECT 1"),


    /**
     * SQL Server.
     */
    SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "com.microsoft.sqlserver.jdbc.SQLServerXADataSource", "SELECT 1") {
        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || "SQL SERVER".equalsIgnoreCase(productName);
        }

    },

    /**
     * Teradata.
     */
    TERADATA("Teradata", "com.teradata.jdbc.TeraDriver");
    private String productName;

    private String driverClassName;

    private String xaDataSourceClassName;

    private String validationQuery;


    DatabaseDriverEnum(String productName, String driverClassName) {
        this.productName = productName;
        this.driverClassName = driverClassName;
    }

    DatabaseDriverEnum(String productName, String driverClassName, String xaDataSourceClassName) {
        this.productName = productName;
        this.driverClassName = driverClassName;
        this.xaDataSourceClassName = xaDataSourceClassName;
    }

    DatabaseDriverEnum(String productName, String driverClassName, String xaDataSourceClassName, String validationQuery) {
        this.productName = productName;
        this.driverClassName = driverClassName;
        this.xaDataSourceClassName = xaDataSourceClassName;
        this.validationQuery = validationQuery;
    }

    /**
     * Return the identifier of this driver.
     *
     * @return the identifier
     */
    public String getId() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    protected boolean matchProductName(String productName) {
        return this.productName != null && this.productName.equalsIgnoreCase(productName);
    }

    public static DatabaseDriverEnum matchDriver(String urlWithoutJDBC) {
        for (DatabaseDriverEnum driver : DatabaseDriverEnum.values()) {
            String prefix = ":" + driver.name().toLowerCase(Locale.ENGLISH) + ":";
            if (!driver.equals(UNKNOWN) && urlWithoutJDBC.startsWith(prefix)) {
                return driver;
            }
        }
        return UNKNOWN;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getXaDataSourceClassName() {
        return xaDataSourceClassName;
    }

    public void setXaDataSourceClassName(String xaDataSourceClassName) {
        this.xaDataSourceClassName = xaDataSourceClassName;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}
