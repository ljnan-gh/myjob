package myjob.core.db.mysql;

import lombok.Data;

@Data
public class DBSourceInfo {
    private String url;
    private String username;
    private String password;
    private String datasourceId;
    private String driver;
}
