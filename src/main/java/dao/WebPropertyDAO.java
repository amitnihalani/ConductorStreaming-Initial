package dao;

import beans.ComparisonWebProperty;
import beans.WebProperty;
import jdbc.JDBCConnection;

import java.sql.*;

/**
 * Created by anihalani on 6/9/15.
 */
public class WebPropertyDAO {
    /**
     * Writes the values in the WebProperty object to the 'web_property' table in the local database
     * 
     * @throws Exception
     *             - if connection is null
     */
    public static void writeToDatabase(WebProperty webProperty) throws Exception {
        Connection conn = JDBCConnection.getConnection();
        if (conn == null) {
            throw new Exception("Connection not successful!");
        }
        // Execute a query
        // the mysql insert statement
        String query = "insert into web_property (web_property_id, name) values (?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, webProperty.getWebPropertyId());
            preparedStmt.setString(2, webProperty.getName());
            // preparedStmt.setString(3, webProperty.);
            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error in closing connection!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the values in the WebProperty object to the 'web_property' table in the local database
     * 
     * @throws Exception
     *             - if connection is null
     */
    public static void writeToDatabase(ComparisonWebProperty webProperty) throws Exception {
        Connection conn = JDBCConnection.getConnection();
        if (conn == null) {
            throw new Exception("Connection not successful!");
        }

        if(webPropertyExists(webProperty.getWebPropertyId(), conn)){
            return;
        }
        // Execute a query
        // the mysql insert statement
        String query = "insert into web_property (web_property_id, name, label) values (?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, webProperty.getWebPropertyId());
            preparedStmt.setString(2, webProperty.getName());
            preparedStmt.setString(3, webProperty.getLabel());
            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error in closing connection!");
                e.printStackTrace();
            }
        }
    }

    private static boolean webPropertyExists(int webPropertyId, Connection conn) throws Exception {
        if (conn == null) {
            throw new Exception("Connection not successful!");
        }

        Statement stmt = conn.createStatement();
        // Execute a query
        // the mysql select statement
        String query = "select * from web_property where web_property_id=" + webPropertyId;
        ResultSet rs = stmt.executeQuery(query);

        if (rs == null) {
            return false;
        }
        return true;
    }
}
