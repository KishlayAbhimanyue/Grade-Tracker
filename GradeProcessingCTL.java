import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/*
 Created by Bikram Shrestha
 This class controls the mysql database by generating various sql query
 and passing it to the method in the GradeJdbc class. This class also
 handles all the input out validation that need to be taken while
 carrying out various functionality within the application.
 The data for table is generated and maintained within this class.
 */
//Enum state to determine the operation that user want to perform.
enum State{ADD, UPDATE,SEARCH,POPULATE,CREATE,CHECK}

public class GradeProcessingCTL{

    protected State state;	//Enum to determine the state of Sql operation
	//New GradeJdbc is created to manipulate sql database.
    private GradeJdbc gradeJdbc = new GradeJdbc();
    // Arraylist of Student is created to convert into observableList.
	protected  ArrayList<StudentRecord> studentList = new ArrayList<>();
	// ObservableList called data is created to displaying data in table.
	ObservableList<StudentRecord> data=
			FXCollections.observableArrayList(studentList);

	
	/*
	This method takes student id , name , quiz, a1, a2, a3
	and exam marks to calculate result and grade and return
	StudentRecord object.
	 */
	public StudentRecord createStudentRecord (
		int idNumber,String studentName,
		int quiz, int a1, int a2,
		int a3, int exam) {
		String result = (calculateResults(quiz, a1, a2, a3, exam));
		String grade = calculateGrade(Double.parseDouble(result));
		StudentRecord studentRecord = new StudentRecord (idNumber, 
			studentName, quiz, a1, a2, a3, exam, result, grade);
		return studentRecord;
	}


	/*
	Result is created with data provide using the formula
	(quiz * 0.05) + (a1 * 0.15) + (a2 * 0.2) + (a3 * 0.10) + (exam * 0.5)
	and the result is returned as string so that the data is ready to be
	displayed in table as desired.
	 */
	public String calculateResults (int quiz,
		int a1, int a2, int a3, int exam) {
		double result = (quiz * 0.05) + (a1 * 0.15) + 
		(a2 * 0.2) + (a3 * 0.10) + (exam * 0.5);
		//return result;
		String stringResult = String.format("%.2f",result);
		return stringResult;
	}


	/*
	calculateGrade() take result and will return a string as suitable to
	the various criteria.
	 */
	public String calculateGrade(double result){
		String grade;
		if (result >= 85) {	//More or equal to 85 is HD
			grade = "HD";
		}
		else if (result >= 75) { // More or equal to 75 it DI
			grade = "DI";
		}
		else if(result >= 65) {	// More or equal to 65 is CR
			grade = "CR";
		}
		else if (result >= 50) {	// More or equal to 50 is PS
			grade = "PS";
		}
		else {
			grade = "FL";	// Otherwise FL
		}
		return grade;
	}


	/*
	This method call checkTable in GradeJdbc which will return
	boolean value according to table existence.
	 */
	public boolean checkTable() {
		return gradeJdbc.checkTable();
	}


	/*
	populateTableWithAllData() look at the table and if table is
	present then it will look at the database table and get all
	the value in the form of arraylist of student record object
	and update the data for table with the newly generated value.
	 */
	public void populateTableWithAllData() {
	    state = State.POPULATE;	//Set the state to populate.
        if(!gradeJdbc.checkTable()){
        	// Print statement for tracking code execution.
            System.out.println("No table found.");
        }
        // Sql query to select all the information from Java2.
        String query = "SELECT * FROM Java2";
        // Method call to return all the value as studentList.
        studentList= gradeJdbc.sqlQueryHandler(state,query);
        data.addAll(studentList); //studentList is added to data.
	}


	/*
	This method create an sql query to create a new table.
	It will check if he table exist in database or not. If the
	table is present the table in database is deleted and a new
	table is created by executing the create table sql query.
	 */
    public void createTable() {
	    state = State.CREATE;	//state for sql query execution.
        //Sql Query to create a new table
        String createTable =
                "CREATE TABLE Java2(studentId CHAR (8)," +
                        "studentName VARCHAR (50)," +
                        "quiz INTEGER ," +
                        " a1 INTEGER , " +
                        "a2 INTEGER , " +
                        "a3 INTEGER , " +
                        "exam INTEGER , " +
                        "result VARCHAR(6), " +
                        "grade CHAR (2), " +
                        "PRIMARY KEY (studentId))";
        //If table exist then delete the table
        if(checkTable()){
        	//Sql query for deleting table.
            String dropTable = "DROP TABLE Java2";
            gradeJdbc.sqlQueryHandler(state,dropTable);
        }
        // If table doesnot exist create table without deleting.
        gradeJdbc.sqlQueryHandler(state,createTable);
        data.clear();	//table data is cleared as nothing in database.
    }


    /*
    This method will check the provided studentId with the student id
    in the student record object in studentList. If the id match then
    the id already exist else does not exist.
     */
    public boolean studentIdExist(int studentId){
		for(int i = 0; i < studentList.size(); i++) {
			int studentIDsInDataBase = studentList.get(i).getIdNumber();
			if (studentId == studentIDsInDataBase) {
				return true;
			}
		}
		return false;
	}


    /*
    addStudentIntoDatabase take the StudentRecord object and extract
    information out of it and create an sql query to insert the data
    in sql database. As sqlQueryHandler() return studentRecordList, the
    database is refreshed with the updated database.
     */
    public boolean addStudentIntoDatabase(StudentRecord studentRecord){
        int studentId = studentRecord.getIdNumber();
        // If student ID exist then it will not get inserted into database.
        if(studentIdExist(studentId)){
        	return false;
		}
        state = State.ADD; // state is updated for adding data in sql database.
		// Sql query for inserting data is created from studentRecord object.
        String query = "INSERT INTO Java2 VALUES ('" + studentRecord.getIdNumber() +
                "','" + studentRecord.getStudentName() + "'," +
                studentRecord.getQuiz() + "," +
                studentRecord.getA1() + "," +
                studentRecord.getA2() + "," +
                studentRecord.getA3() + "," +
                studentRecord.getExam() + "," +
                studentRecord.getResult() + ",'" +
                studentRecord.getGrade() + "')";
        // Query is executed using sqlQueryHandler
        gradeJdbc.sqlQueryHandler(state,query);
		// Print statement for tracing execution of code.
		System.out.println("studentRecord added:" + studentRecord);
        data.add(studentRecord); // data is updated with new studentRecord.
        return  true;
    }


	/*
	searchDatabase take information passed through textField as well as checkBox information
	to make sure correct data is captured by textfield. After than the sql query for
	looking at database created. The updated studentRecordList is added to the data after
	emptying the data for table so that only search result is shown.
 	*/
    public void searchDatabase(String comboBox, String textField){
        state = State.SEARCH; // state is set to SEARCH for sql query execution
        String[] formatOfDatabase = {	// Array of database table model
        		"studentId",
				"studentName",
				"quiz" ,
				"a1",
				"a2",
				"a3",
				"exam",
				"result",
				"grade"
        };
        String[] searchArray = {	// Array of ComboBox value.
        		"ID",
				"Name",
				"Quiz",
				"Assignment 1",
				"Assignment 2",
				"Assignment 3",
				"Exam",
				"Result",
				"Grade"
        };

        int index = 100;	// Assigning arbitrary value to index.
		// Matching the value of combobox to array to get index.
        for(int i = 0; i < searchArray.length; i++){
            if(searchArray[i].equals(comboBox)) {
                index = i;
            }
        }
        //Once index is found we know the columnName in sql database by matching
		// index as they are aligned symmetrically in array above.
        String columnName = formatOfDatabase[index];
        String searchTerm = textField;	//search term is string present in textField.
		//Creating searchQuery for general column value.
        String searchQuery = String.format(
        		"SELECT * FROM Java2 WHERE  %s  LIKE '%s'",columnName,searchTerm);
		//Creating searchQuery for name and result to accept wildcards.
        if(index == 1 || index ==7){
            searchQuery = String.format(
            		"SELECT * FROM Java2 WHERE  %s  LIKE '%s'",columnName,searchTerm+"%");
        }
        // Getting studentList after executing sql query.
        studentList = gradeJdbc.sqlQueryHandler(state,searchQuery);
        //If there is data in studentList then the data in table is cleared so that
		// search result can be presented.
        if(studentList!=null){
            data.clear();
            data.addAll(studentList);
        }
    }


    /*
    updateDatabase() take a studentRecord object and create a sql query out of it and it
    is executed using the sqlQueryHandler() and the returned studentlist is added to the
    table after clearing the value in table to remove duplicate content.
     */
    public void updateDatabase(StudentRecord studentRecord){
        state = State.UPDATE;	//state is set to UPDATE sql query.

		// Update query is constructed.
        String updateQuery =String.format("UPDATE Java2 SET studentName= '%s'," +
                        "quiz='%d' ,a1='%d',a2='%d',a3='%d',exam='%d',result='%s'," +
                        "grade='%s' WHERE studentId='%d'",
                studentRecord.getStudentName(),studentRecord.getQuiz(),
                studentRecord.getA1(), studentRecord.getA2() ,
                studentRecord.getA3() , studentRecord.getExam() ,
                studentRecord.getResult() , studentRecord.getGrade(),
                studentRecord.getIdNumber()
        );
        //Print statement for tracing execution of code.
        System.out.println(updateQuery);
        // query is executed using sqlQueryHandler
        gradeJdbc.sqlQueryHandler(state,updateQuery);
        data.clear();
        populateTableWithAllData();
    }


    /*
    inputMarksCheck() take string value and check whether the input value
    is bigger or equal to zero and smaller or equal to 100. If the
    value satisfy the criteria then boolean value true is returned else
    false is returned.
     */
	public boolean inputMarksCheck(String string){
		try{ //Can it be converted to int.
			int mark = Integer.parseInt(string);
			//If it can does it satisfy required criteria.
		if (mark>=0 && mark <= 100){
			return true;	//If it does return true.
		}
		return false; // Else false.
		}
		catch(NumberFormatException ex){
			return false;	// If error false.
		}
	}


	/*
	inputIDcheck() check whether the value passed as string is integer
	value and the length is equal to 8.
	 */
	public boolean inputIDcheck(String string){
		if(string.length() == 8){ //Is length equal to 8
			try{
				// Can it be converted to int.
				int id = Integer.parseInt(string);
				return true; // Return true.
			}
			catch(NumberFormatException e){
				return false;	//Else return false.
			}
		}
		return false; //Else return false.
	}


	/*
	inputNameCheck() check whether the value passed is alphabet only.
	If it contain number then it return false.
	 */
	public boolean inputNameCheck(String string){
		for (int i = 0; i < string.length(); i++) {
			if ((Character.isDigit(string.charAt(i)))) {
				return false;
			}
		}
		return true;
	}


	/*
	inputGradeCheck() check the user input for grade search.
	It will match whether it is same as that of value of items
	in grade array and will return true if it matches and return false
	if the value does not match.
	 */
	public boolean inputGradeCheck(String string){
	    String[] grade = {"HD", "DI","CR","PS","FL"};
	    for(String item: grade){
	        if (item.equals(string.toUpperCase())){
	            return true;
            }
        }
        return  false;
    }


	/*
	This method checkInputError() is being used to check everything
	if the user decide to ignore all the warning and try to proceed
	anyway.
	 */
	public ArrayList<Integer> checkInputError(String[] string){
		boolean id = inputIDcheck(string[0]);
		boolean name = inputNameCheck(string[1]);
		boolean quiz = inputMarksCheck(string[2]);
		boolean a1 =inputMarksCheck(string[3]);
		boolean a2 =inputMarksCheck(string[4]);
		boolean a3 =inputMarksCheck(string[5]);
		boolean exam =inputMarksCheck(string[6]);
		boolean[] errorCheck = {id,name,quiz,a1,a2,a3,exam};
		ArrayList<Integer> error = new ArrayList<>();
		for(int i = 0; i <errorCheck.length;i++)
            if (errorCheck[i] == false) {
                error.add(i);
            }
		return error;
	}


	/*
	This method is used to check the comboBox selection and the value
	entered in the textfield is valid. It  will return boolean value.
	 */
	public boolean errorSearch(String comboBox, String textField){
        String[] searchArray = {
        		"ID",
				"Name",
				"Quiz",
				"Assignment 1",
				"Assignment 2",
				"Assignment 3",
				"Exam",
				"Result",
				"Grade"
        };
        int index = 100;
        for(int i = 0; i < searchArray.length; i++){
            if(searchArray[i].equals(comboBox)) {
                index = i;
            }
        }
        if(index == 0){
            return inputIDcheck(textField);
        }
        else if(index == 1){
            return inputNameCheck(textField);
        }

        else if(index < ((searchArray.length)-1) ){
            return inputMarksCheck(textField);
        }

        else if(index == ((searchArray.length)-1)){
            return inputGradeCheck(textField);
        }
        return false;
    }
}
