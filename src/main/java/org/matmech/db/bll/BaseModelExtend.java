package org.matmech.db.bll;

import org.matmech.db.models.BaseModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseModelExtend extends BaseModel {
    protected final Connection connection;
    protected final Statement statement;

    public BaseModelExtend(Connection connection) {
        try {
            this.connection = connection;
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
