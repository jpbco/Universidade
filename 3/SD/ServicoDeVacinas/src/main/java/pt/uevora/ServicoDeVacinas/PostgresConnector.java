package pt.uevora.ServicoDeVacinas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jpbc
 */
public class PostgresConnector {

    private String PG_HOST;
    private String PG_DB;
    private String USER;
    private String PWD;

    Connection con = null;
    Statement stmt = null;

    public PostgresConnector() {
        Properties prop = getProperties();
        this.PG_HOST = prop.getProperty("host");
        this.PG_DB = prop.getProperty("bd");
        this.USER = prop.getProperty("user");
        this.PWD = prop.getProperty("pw");

    }

    public void connect() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://" + PG_HOST + ":5432/" + PG_DB, USER, PWD);

            stmt = con.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problems setting the connection");
        }
    }

    public void disconnect() {
        try {
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return stmt;
    }

    private Properties getProperties() {
        String propertiesFile = "application.properties";
        ClassLoader cl = this.getClass().getClassLoader();
        Properties prop = new Properties();
        try {
            var in = cl.getResourceAsStream(propertiesFile);
            prop.load(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PostgresConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PostgresConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prop;
    }
}
