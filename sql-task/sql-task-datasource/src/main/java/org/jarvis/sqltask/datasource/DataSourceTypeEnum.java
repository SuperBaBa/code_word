package org.jarvis.sqltask.datasource;

public enum DataSourceTypeEnum {
    DRUID("druid", "com.alibaba.druid.pool.DruidDataSource"),
    HIKARI("hikari", "com.zaxxer.hikari.HikariDataSource"),
    DPCP2("dpcp2", "org.apache.commons.dbcp2.BasicDataSource"),
    TOMCAT("tomcat", "org.apache.tomcat.jdbc.pool.DataSource"),

    UNKNOWN_DATASOURCE("unknown", null);


    private String typeName;
    private String dataSourceReferenceName;

    DataSourceTypeEnum(String typeName, String dataSourceClazz) {
        this.typeName = typeName;
        this.dataSourceReferenceName = dataSourceClazz;
    }

    public static DataSourceTypeEnum matchDataSourceType(String typeName) {
        for (DataSourceTypeEnum dataSource : DataSourceTypeEnum.values()) {
            if (dataSource.typeName.equals(typeName)) {
                return dataSource;
            }
        }
        return UNKNOWN_DATASOURCE;
    }

    public static DataSourceTypeEnum matchDataSourceType(DataSourceTypeEnum dataSourceTypeEnum) {
        for (DataSourceTypeEnum dataSource : DataSourceTypeEnum.values()) {
            if (dataSourceTypeEnum.equals(dataSource)) {
                return dataSource;
            }
        }
        return UNKNOWN_DATASOURCE;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDataSourceReferenceName() {
        return dataSourceReferenceName;
    }

    public void setDataSourceReferenceName(String dataSourceReferenceName) {
        this.dataSourceReferenceName = dataSourceReferenceName;
    }
}
