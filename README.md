# Grade-Processing-Application
This is the Java application created using JavaFX and JDBC to save and retrieve the student score and do CRUD.
## Logic

Grade Processing application fully depend on the establishing connectivity with JDBC and existence of required database in the connected machine. MySQL server has been used to host the database.  It assumes that the MySQL server has a database called ‘GradeProcessing’ present in the system. Appropriate connector and driver are also installed in the system. 

Grade Processing application use dialog box with alert to make sure only one operation is being performed. The input data has been validated vigorously and the application will not allow the user to input any invalid input. If the user input invalid data, an alert will pop up to notify the user about the mistake made.

There are four java files which serve special purpose to run the application. 

1.	StudentRecord.java
2.	GradeJdbc.java
3.	GradeProcessingCTL.java
4.	GradeGUI.java

#### 1. StudentRecord.java

-	This class is used as a model class for storing all the information regarding student's record. 
-	Constructor for creating StudentRecord by passing all the required data is included to create StudentRecord object.
-	This model class provide method to get and set the value of the student which includes id, student name, marks obtained in quiz, assessment 1,2 and 3 as well as from exam, as well as result and grade. 

#### 2.	GradeJdbc.java

-	This is a class that take care of Java Database Connectivity as well as MySQL operations. 
-	Methods for creating a connection and executing SQL query as well as checking table have been provided.

#### 3.	GradeProcessingCTL.java
-	This class controls the MySQL database by generating various SQL query and passing it to the method in the GradeJdbc class. 
-	This class also handles all the input out validation that need to be taken while carrying out various functionality within the application.
-	The data for table is generated and maintained within this class.

#### 4.	GradeGUI.java
-	GradeGUI uses GradeProcessingCTL.java to manipulate the data in the database.
-	This class provide user with the Graphic User Interface to interact with various feature of the application. 
-	This application uses JavaFX and uses combination of Dialog, Alert, Grid View,
Button, Label, Text Field and ComboBox to get the desired result.


### Input: - 
This application depends on JDBC and firstly it will get connection with default configuration to the database provided.  All the data for the table view is extracted from the database “GradeProcessing” and table “Java2”After that the user can use the combination of button, Text field, combo box to input data in the database.

-	Firstly, the input from the user come to system in the form of button click or click on the table itself. 
-	Once the user selects the desired function, a popup window is displayed using an alert to make sure that the user complete one transection before trying other operation.
-	Text field and ComboBox has been used to get user input.

### Processing: -
As user is going to interact with the graphic interface, the processing of the user interaction has a lot of variation.
 
If user select  Create Table:

-	If user select Create Table, it will run a method that check whether table exist or not, if table exist an alert is displayed regarding it and will let user continue if desired.
-	If user  decide to continue or if there was no table, the user can press ok in alert to create a table which is formatted as specified in the assignment briefing.

If user select Insert Record:

-	If user select Insert Record, an alert containing dialog box with a grid of label and text field is presented where user can input all the value to be put in the system.
-	All the input is checked for validity in real time and will present with relevant information to notify of error made.
-	If there is no error in the input fields then when user press OK button, the data will be stored in MySQL database, which is reflected in table and if there is error an alert is presented with an error message.

If user select Search:

-	If user select Search, an alert with a dialog containing ComboBox and text field is presented.
-	User can select a field to search by selecting item in the ComboBox and input search term in text field.
-	If there is no error and data with the search field exist, then the table only show the result.
-	This is also shown by updating the label to let user know that table is only showing search result. 
-	If there is error in input, then an error message is presented in alert.
-	Once the search operation is performed, the user can select any other button to reset the table to show all the data in database.

If user select Update:

-	If user select Update without selecting the data to be updated, then an error message is presented to let user know that user need to select the data from the tablet o be updated.
-	If user has already selected the data in table, an alert containing dialog box with a grid of label and text field is presented where user can input all the value to be put in the system.
-	All the input is checked for validity in real time and will present with relevant information to notify of error made.
-	If there is no error in the input fields then when user press OK button, the data will be updated in MySQL database, which is reflected in table and if there is error an alert is presented with an error message.

If user select Calculate Result or Grade:

-	If user select Calculate Result or Grade, an alert with a dialog containing a grid pane of label  and text field is presented where user can input all the value to be used to do calculation.
-	All the input is checked for validity in real time and will present with relevant information to notify of error made.
-	If there is no error, an alert is used to display the result of the calculation.
-	If there is error, an alert is used to display the mistake made.

### Output: -
-	Relevant information is presented in either table view or using the alert with the User interface.
-	The data that has been created or modify will be reflected in the database.
