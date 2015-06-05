package sql;
import generated.Location;

import java.sql.*;
/**
 * Created by anihalani on 5/29/15.
 */
public class JDBCConnection {
    // JDBC drivers
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static Connection conn = null;
    public static Statement stmt = null;

    /**
     * Method to cteate a JDBC connection and return it.
     * @return  JDBC Connection
     */
    public static Connection getConnection() {

        try{
            //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            return DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
    }

    private static void PrintTable(Connection conn, Statement stmt, String tableName){
        ResultSet rs = null;
        try {
            String sql = "Select * from "+tableName+";";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString(1) + "   "
                        + rs.getString(2) + "   "
                        + rs.getString(3) + "   "
                        + rs.getString(4) + "   "
                        + rs.getString(5) + "   "
                        + rs.getString(6) + " ");
            }
        } catch (SQLException e) {
            System.out.println("Error in PrintTable method");
            e.printStackTrace();
        }

        System.out.println("Read records from the table...");
        System.out.println("Local_ID Description Country State City Locode");
    }

    public static void WriteToTable(String tableName, Location location){
        conn = getConnection();
        //STEP 4: Execute a query
        //System.out.println("Inserting records into the table...");

        String sql = "INSERT INTO "+tableName+"  (description) " +
                "values ('"+location.getDescription()+"');";

        if (stmt != null) {
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        conn.close();
                }catch(SQLException se){
                }// do nothing
                try{
                    if(conn!=null)
                        conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }//end try
        }
    }

}//end JDBCExample

