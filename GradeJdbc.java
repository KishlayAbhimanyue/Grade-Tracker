import java.sql.*;
import java.util.ArrayList;

/*
Created by Bikram Shrestha
This is a class that take care of Java Database Connectivity as well as MySQL operations. Methods for creating
a connection and executing sql query as well as checking table have been provided.
=> defaultConnection()
    This method use the hardcoded database URL as well as username and password to connect with the MySQL
    database in the local system. This method can be altered to make it connect with any sql database and
    perform the operations that follows.

=> checkTable()
    This method will look at the sql database metadata and check whether there is table with Java2 name.
    If present it will return boolean value true and if not will return false.

=> sqlQueryHandler(State state, String query)
    This method take enum State with query. State determine whether to run a queryUpdate and queryExecute
    as well as other functionality. Special care has been taken to make sure ResultSet, Statement and
    Connection has been closed properly.
 */
public class GradeJdbc {

    boolean tableExist = false; // tableExist is being used to check whether the table exist in database.

    /* This method use the hardcoded database URL as well as username and password to connect with the MySQL
    database in the local system. This method can be altered to make it connect with any sql database and
    perform the operations that follows. This method will try to make connection and if successful will
    return the correct connection to the sql otherwise will return connection with null value.
     */
    public  Connection defaultConnection() {
        Connection connection = null;   //Declaring and assigning null to connection.
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Register JDBC Driver

            System.out.println("Creating a connection..."); // Print Statement for tracing code execution.

            //DBURL has been hardcoded to connect to local host with database name GradeProcessing.
            String DBURL = "jdbc:mysql://localhost/GradeProcessing?allowPublicKeyRetrieval=true&useSSL=false";
            String USER         = "root";       //Sql user name has been hardcoded.
            String PASSWORD     = "password";   //Sql password is hardcoded
            connection = DriverManager.getConnection(DBURL, USER, PASSWORD); //Open a connection
            System.out.println("Database connected");   // Print Statement for tracing code execution.
            return connection;  //If everything is successful connection is returned.
        }
        catch (Exception e) {
            // Print Statement for tracing code execution.
            System.out.println("Unable to establish connection with Database with default setting.");
            return connection;  //Else connection with null value is returned.
        }

    }


    /*
    This method will look at the sql database metadata and check whether there is table with Java2 name.
    If present it will return boolean value true and if not will return false.
     */
    public boolean checkTable()  {
        State state = State.CHECK;  // Setting the correct state value for sql query execution.
        String query = "";          // Using fake query to use sqlQueryHandler.
        sqlQueryHandler(state,query);   // Executing the method which will alter the boolean value.
        return tableExist;
    }


    /*
    This method take enum State with query. State determine whether to run a queryUpdate and queryExecute
    as well as other functionality. Special care has been taken to make sure ResultSet, Statement and
    Connection has been closed properly. This method will return ArrayList of StudentRecord where required.
     */
    public ArrayList<StudentRecord> sqlQueryHandler(State state, String query){
        //studentRecordList to store any studentRecord created during operations.
        ArrayList<StudentRecord> studentRecordList =  new ArrayList<>();
        Connection connection = null;   // Declaring connection with null;
        Statement statement = null;     // Declaring statement with null;
        ResultSet resultSet = null;     // Declaring resultSet with null;
        try {
            connection = defaultConnection();   //Creating connection using defaultConnection();
            statement = connection.createStatement();   // Creating statement.
            /*
            If the state value is State.ADD, State.UPDATE or State.CREATE, then we need to use
            executeUpdate, so this condition make sure that correct statement is executed.
             */
            if(state == State.ADD || state == State.UPDATE || state == State.CREATE){
                statement.executeUpdate(query);
                // Print Statement for tracing code execution.
                System.out.println("Executing SQL query");

            }
            /*If state is State.SEARCH or State.POPULATE, then executeQuery is used and
            resultSet is created and data is assigned in loop to create a StudentRecord object.
             */
            else if(state==State.SEARCH || state == State.POPULATE){
                resultSet = statement.executeQuery(query);  // Assigning resultSet.
                // Print Statement for tracing code execution.
                System.out.println("Executing SQL query");
                while (resultSet.next()) {
                    GradeProcessingCTL gradeProcessingCTL = new GradeProcessingCTL();
                    StudentRecord newStudent = gradeProcessingCTL.createStudentRecord(
                            resultSet.getInt(1),    // Student Id
                            resultSet.getString(2), // Student Name
                            resultSet.getInt(3),    // Quiz
                            resultSet.getInt(4),    // Assignment 1
                            resultSet.getInt(5),    // Assignment 2
                            resultSet.getInt(6),    // Assignment 3
                            resultSet.getInt(7));   // Exam
                    studentRecordList.add(newStudent);
                }
            }
            else if(state ==State.CHECK){
                DatabaseMetaData dbm = connection.getMetaData(); //databasemetadata is created.
                resultSet = dbm.getTables(null, null, "Java2", null);
                tableExist = resultSet.next();  //If table exist then tableExist value is assigned.
            }
        }
        catch (SQLException e) {    //Any Sql exception is handled.
            e.printStackTrace();
        }
        finally {   //Finally all the ResultSet, Statement and Connection is closed.
            if(resultSet!=null){
                try{
                    resultSet.close();  //First ResultSet is closed.
                }
                catch (SQLException er){

                }
                if(statement!=null){
                    try{
                        statement.close();  //Then Statement.
                    }
                    catch (SQLException es){

                    }
                    if(connection!=null){
                        try {
                            connection.close(); //Finally Connection is closed.
                            // Print Statement for tracing code execution.
                            System.out.println("Everything has been closed.");
                        }
                        catch (SQLException ec){

                        }
                    }
                }
            }
        }
        return studentRecordList;   //This method return ArrayList of StudentRecord.
    }
}

