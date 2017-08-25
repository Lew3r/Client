package Client;

import java.sql.Connection;
import java.sql.Statement;

public class Database {
    Connection conn;
    Statement stmt;
    public Database(Connection conn,Statement stmt)
    {
        this.conn=conn;
        this.stmt=stmt;
    }
    public Connection returnConnection()
    {
        return this.conn;
    }
    public Statement returnStatement()
    {
        return this.stmt;
    }

}