import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

/*
Created by Bikram Shrestha
 This class provide user with the Graphic User Interface to interact
 with various feature of the application. This application uses only
 one stage to operate and uses combination of Dialog, Alert, GridView,
 Button, Label, TextField and ComboBox to get the desired result.

 */
public class GradeGUI extends Application {
    /* Enum called FunctionState has been declared to link the
     enum value with the key press for determine functionality.*/
    private enum FunctionState{
        UPDATE,
        SEARCH,
        INSERT,
        CREATE_TABLE,
        CALCULATE_RESULT,
        CALCULATE_GRADE
    }
    //functionState holds the value of FunctionState.
    private FunctionState functionState = null;

    //Various textField has been initialise to get the user input.
    private TextField tfStudentId = new TextField();
    private TextField tfStudentName = new TextField();
    private TextField tfQuiz = new TextField();
    private TextField tfA1 = new TextField();
    private TextField tfA2 = new TextField();
    private TextField tfA3 = new TextField();
    private TextField tfExam = new TextField();


    // Bottom for various functions of application.
    private Button btCreateTable = new Button("Create Table");
    private Button btInsertRecord = new Button("Insert Record");
    private Button btSearch = new Button("Search");
    private Button btUpdate = new Button("Update");
    private Button btCalculateResult = new Button("Calculate Result");
    private Button btCalculateGrade = new Button ("Calculate Grade");

    // Labels to label the textBox.
    private Label labelId = new Label("Student ID: ");
    private Label labelName = new Label("Student Name: ");
    private Label labelA1 = new Label("Assignment 1:");
    private Label labelA2 = new Label("Assignment 2:");
    private Label labelA3 = new Label("Assignment 3:");
    private Label labelQuiz = new Label("Quiz:");
    private Label labelExam = new Label("Exam:");

    //UI label to welcome and provide table information
    private Label mainLabel = new Label("Student Grade details.");

    // Alert has been used to get interaction from user to carryout
    // data insert, update, search,calculate and create table.
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert info = new Alert(Alert.AlertType.INFORMATION);

    // GridPane for the main stage of the application.
    private GridPane gridPane = new GridPane();

    // Labels to indicate whether there were any errors during data insertion.
    private Label studentIdErrorLabel = new Label();
    private Label studentNameErrorLabel = new Label();
    private Label a1ErrorLabel = new Label();
    private Label a2ErrorLabel = new Label();
    private Label a3ErrorLabel = new Label();
    private Label quizErrorLabel = new Label();
    private Label examErrorLabel = new Label();


    // New GradeProcessingCTL has been initialised.
    private GradeProcessingCTL gradeControl = new GradeProcessingCTL();

    // This is a start method that was override in application.
    @Override
    public void start(Stage primaryStage){

        // Minimum width of textfield of quiz has setup so that setPrompt
        // command can show all the text in textfield.
        tfQuiz.setMinWidth(182);

        // Prompt text has been set for all the textfield for showing
        // user with information about which data is required.
        tfStudentId.setPromptText("ID must be 8 digit number");
        tfStudentName.setPromptText("Should not contain number");
        tfQuiz.setPromptText("Whole number (0 to 100)");
        tfA1.setPromptText("Whole number (0 to 100)");
        tfA2.setPromptText("Whole number (0 to 100)");
        tfA3.setPromptText("Whole number (0 to 100)");
        tfExam.setPromptText("Whole number (0 to 100)");

        /*
        tfStudentId gets the student id, if the student id is either not
        valid or is already present then a errorLabel is shown in the
        side of the textField to indicate user about their mistakes.
         */
        tfStudentId.setOnKeyReleased(e -> {
            if(!(gradeControl.inputIDcheck(tfStudentId.getText()))){
                studentIdErrorLabel.setText("ID must be 8 digit number");
                studentIdErrorLabel.setTextFill(Color.RED);
            }
            else if (gradeControl.studentIdExist(
                       Integer.parseInt(tfStudentId.getText()))) {
                studentIdErrorLabel.setText("ID already exist");
                studentIdErrorLabel.setTextFill(Color.RED);
            }
            else{
                studentIdErrorLabel.setText("");
            }
        });
        /*
        tfStudentName gets the student name, if the student name contains
        digit instead of albhabets only then errorLabel is shown in the
        side of the textField to indicate user about their mistakes.
         */
        tfStudentName.setOnKeyReleased(e -> {
            if(!gradeControl.inputNameCheck(tfStudentName.getText())){
                studentNameErrorLabel.setText("Should not contain number");
                studentNameErrorLabel.setTextFill(Color.RED);
            }
            else{
                studentNameErrorLabel.setText("");
            }
        });

        /*
        tfA1 gets the student assignment 1 marks, if the marks is not
        0 to 100 then an errorLabel is shown in the side of the textField
        to indicate user about their mistakes.
         */
        tfA1.setOnKeyReleased(e -> {
            if(!gradeControl.inputMarksCheck(tfA1.getText())){
                a1ErrorLabel.setText("Whole number (0 to 100)");
                a1ErrorLabel.setTextFill(Color.RED);
            }
            else{
                a1ErrorLabel.setText("");
            }
        });

        /*
        tfA2 gets the student assignment 2 marks, if the marks is not
        0 to 100 then an errorLabel is shown in the side of the textField
        to indicate user about their mistakes.
         */
        tfA2.setOnKeyReleased(e -> {
            if(!gradeControl.inputMarksCheck(tfA2.getText())){
                a2ErrorLabel.setText("Whole number (0 to 100)");
                a2ErrorLabel.setTextFill(Color.RED);
            }
            else{
                a2ErrorLabel.setText("");
            }
        });

        /*
        tfA3 gets the student assignment 3 marks, if the marks is not
        0 to 100 then an errorLabel is shown in the side of the textField
        to indicate user about their mistakes.
         */
        tfA3.setOnKeyReleased(e -> {
            if(!gradeControl.inputMarksCheck(tfA3.getText())){
                a3ErrorLabel.setText("Whole number (0 to 100)");
                a3ErrorLabel.setTextFill(Color.RED);
            }
            else{
                a3ErrorLabel.setText("");
            }
        });

        /*
        tfQuiz gets the student quiz  mark, if the marks is not 0 to 100
        then an errorLabel is shown in the side of the textField to
        indicate user about their mistakes.
         */
        tfQuiz.setOnKeyReleased(e -> {
            if(!gradeControl.inputMarksCheck(tfQuiz.getText())){
                quizErrorLabel.setText("Whole number (0 to 100)");
                quizErrorLabel.setTextFill(Color.RED);
            }
            else{
                quizErrorLabel.setText("");
            }
        });

        /*
        tfExam gets the student exam  mark, if the marks is not 0 to 100
        then an errorLabel is shown in the side of the textField to
        indicate user about their mistakes.
         */
        tfExam.setOnKeyReleased(e -> {
            if(!gradeControl.inputMarksCheck(tfExam.getText())){
                examErrorLabel.setText("Whole number (0 to 100)");
                examErrorLabel.setTextFill(Color.RED);
            }
            else{
                examErrorLabel.setText("");
            }
        });

        // data of table is updated with data in database to be visible
        // when the application is run for the first time.
        gradeControl.populateTableWithAllData();

        // Setting up tableView called studentRecordTable
		TableView <StudentRecord> studentRecordTable = new TableView<>();

		/*
		Column for table and column data is setup according to the database structure.
		 */
		TableColumn<StudentRecord, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(80);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
 
        TableColumn<StudentRecord, String> studentNameColumn = new TableColumn<>("Student Name");
        studentNameColumn.setMinWidth(100);
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
 
        TableColumn<StudentRecord, String> quizColumn = new TableColumn<>("Quiz");
        quizColumn.setMinWidth(50);
        quizColumn.setCellValueFactory(new PropertyValueFactory<>("quiz"));
 
        TableColumn<StudentRecord, String> a1Column = new TableColumn<>("A1");
        a1Column.setMinWidth(50);
        a1Column.setCellValueFactory(new PropertyValueFactory<>("a1"));
 
        TableColumn<StudentRecord, String> a2Column = new TableColumn<>("A2");
        a2Column.setMinWidth(50);
        a2Column.setCellValueFactory(new PropertyValueFactory<>("a2"));
 
        TableColumn<StudentRecord, String> a3Column = new TableColumn<>("A3");
        a3Column.setMinWidth(50);
        a3Column.setCellValueFactory(new PropertyValueFactory<>("a3"));
 
        TableColumn<StudentRecord, String> examColumn = new TableColumn<>("Exam");
        examColumn.setMinWidth(50);
        examColumn.setCellValueFactory(new PropertyValueFactory<>("exam"));
 
        TableColumn<StudentRecord, String> resultColumn = new TableColumn<>("Results");
        resultColumn.setMinWidth(50);
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        TableColumn<StudentRecord, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setMinWidth(50);
        gradeColumn.setCellValueFactory(
                new PropertyValueFactory<>("grade"));

        // TableView is constrained from resizing for aesthetic reason.
        studentRecordTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Data for tableView is accessed from gradeControl.data which is observable list.
 		studentRecordTable.setItems(gradeControl.data);
        // All the column data is added to the table view.
        studentRecordTable.getColumns().addAll( idColumn, studentNameColumn, quizColumn,
        	a1Column,a2Column,a3Column,examColumn,resultColumn,gradeColumn);

        /*
        When user select the row in the table, the data is captured and saved in the textField
        so that it can be used for updating the database.
         */
        studentRecordTable.setOnMouseClicked(event -> {
            try {
                StudentRecord student = studentRecordTable.getSelectionModel().getSelectedItem();
                tfStudentId.setText(String.valueOf(student.getIdNumber()));
                tfStudentName.setText(student.getStudentName());
                tfQuiz.setText(String.valueOf(student.getQuiz()));
                tfA1.setText(String.valueOf(student.getA1()));
                tfA2.setText(String.valueOf(student.getA2()));
                tfA3.setText(String.valueOf(student.getA3()));
                tfExam.setText(String.valueOf(student.getExam()));
            }
            catch (Exception ex) {
                System.out.println("Other then select data is being carried out in Table");
            }
        });

        /*
        EventHandler is setup for capturing user interaction and performing correct operation
        as expected.
         */
        EventHandler<ActionEvent> handler = e -> {
            // Data stored in errorLabel and textField is removed when the user enter
            // any button other than btUpdate.
            if( !(e.getSource()== btUpdate)){
                tfStudentId.clear();
                tfStudentName.clear();
                tfQuiz.clear();
                tfA1.clear();
                tfA2.clear();
                tfA3.clear();
                tfExam.clear();
                studentIdErrorLabel.setText("");
                studentNameErrorLabel.setText("");
                quizErrorLabel.setText("");
                a1ErrorLabel.setText("");
                a2ErrorLabel.setText("");
                a3ErrorLabel.setText("");
                examErrorLabel.setText("");
            }

        // Label for Table is setup.
        mainLabel.setText("Student Grade Details");

        /*
        If user select btCreateTable, the functionState is assigned to FunctionState.CREATE_TABLE.
        The createTable() is then called. This method basically check whether table is present and
        if present will show warning to the user saying table exist and whether user want to
        continue with the operation and if user accept to go ahead, new table is created in
        database and this change is reflected in the table of UI.
         */
        if(e.getSource()==btCreateTable){
            functionState = FunctionState.CREATE_TABLE;
            createTable();
        }

        /*When user select either 'Insert' or 'Update' icon then the related function is assigned
        to the functionState, then manipulateTable() method is called. manipulateTable() uses the
        functionState to determine what item to display in the UI and will carryout relevant
        function.
         */
        else if(e.getSource()==btInsertRecord || e.getSource()==btUpdate) {
            System.out.println(e.getSource()+ "button pressed");
            if(e.getSource()==btInsertRecord){
                functionState = FunctionState.INSERT;
            }
            else if(e.getSource()==btUpdate){
                functionState = FunctionState.UPDATE;
            }
            manipulateTable();
        }

        /*
        This section check for button press and if the button pressed is Calculate grade or Calculate
        result, once this is determined, functionState is updated to reflect the button pressed.
        After that calculate() method is called which show a dialog box that user can input with all
        the information to calculate the result or grade and once calculated the result is showed
        as alert.
         */
        else if(e.getSource()==btCalculateGrade || e.getSource()==btCalculateResult) {
            System.out.println(e.getSource()+ " button pressed");
            if(e.getSource()==btCalculateResult){
                functionState = FunctionState.CALCULATE_RESULT;
            }
            else{
                functionState = FunctionState.CALCULATE_GRADE;
            }
            calculate();
        }

        /*
        If 'Search' button is pressed, the functionState is updated to FunctionState.SEARCH and
        search() method is called, search() function take the user input by the help of comboBox
        and textField to determine what item in the database need to be searched. At the end of the
        process the data is updated.
         */
        else if(e.getSource()==btSearch){
            System.out.println(e.getSource() + "Button Pressed.");
            functionState = FunctionState.SEARCH;
            search();
        }
        // populateTable generate data for the table depending on button pressed.
        populateTable();
        };

        //Assigning action handler to the button
        btCreateTable.setOnAction(handler);
        btInsertRecord.setOnAction(handler);
        btCalculateResult.setOnAction(handler);
        btCalculateGrade.setOnAction(handler);
        btSearch.setOnAction(handler);
        btUpdate.setOnAction(handler);

        // HBox is used to arrange button in a row.
        HBox buttonRow = new HBox();
        buttonRow.setAlignment(Pos.CENTER); //Align the hbox to center.
        buttonRow.setSpacing(7);            //Set the spacing between item to 7.
        buttonRow.getChildren().addAll(     // Button is added to the hbox.
                btCreateTable,btInsertRecord,btSearch,btUpdate,btCalculateResult,btCalculateGrade);

        gridPane.setAlignment(Pos.CENTER);  //Item in gridPane is aligned to center.
        //Padding of 10 is setup around the gridPane.
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(buttonRow,0,2);    //hbox is added to gridPane.
        gridPane.add(mainLabel,0,4);    //mainLabel is added.
        gridPane.add(studentRecordTable,0,5);   // table is added to gridPane.

        //scene is created and gridPane is placed in the scene.
		Scene scene = new Scene(gridPane, 700, 500);// Creating a Scene scene
  		primaryStage.setTitle("Grade Processing");	// Set the stage title
  		primaryStage.setScene(scene);// Place the scene in the stage
  		primaryStage.show();    //Stage is displayed.
	}


    /*
    The createTable() method basically check whether table is present and if present will show
    warning to the user saying table exist and whether user want to continue with the operation
    and if user accept to go ahead, new table is created in database and this change is
    reflected in the table of UI.
     */
	private  void createTable(){
        if (gradeControl.checkTable()) {
            info.setTitle("Table already exist");
            info.setHeaderText("Table already exist");
            info.setContentText("The required table already exist.");
            info.showAndWait();
        }
        Dialog<ButtonType> createDialog = new Dialog<>();

        createDialog.setHeaderText("You are about to delete an existing data");
        createDialog.setContentText("Table already exist, Please press OK to create a new Table");
        createDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = createDialog.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("OK Button Pressed");
            gradeControl.createTable();
        }
        else {
            System.out.println("Not changing the database.");
        }
    }


    /*
    manipulateTable() uses the functionState to determine what item to display in the UI and will
    carryout relevant function.
     */
    private void manipulateTable(){
        if(functionState == FunctionState.UPDATE &&(tfStudentId.getText().length()==0)){
            info.setHeaderText("No data has been selected");
            info.setContentText("Please select the data in the table to  update.");
            info.showAndWait();
        }

        else{
            Dialog<ButtonType> dialog = new Dialog<>();

            GridPane grid = new GridPane();
            grid.setMinWidth(500);
            String header = "Press OK to INSERT Data into Database.";
            String title = "Insert Data into Database";
            if(functionState == FunctionState.INSERT){
                grid.add(tfStudentId,2,1);
            }
            if(functionState == FunctionState.UPDATE){
                header= "Press OK to UPDATE Data in Database";
                title = "Update data in Database";
                grid.add(new Label(tfStudentId.getText()), 2, 1);
            }

            grid.add(labelId, 1, 1);
            grid.add(labelName, 1, 2);
            grid.add(labelQuiz, 1, 3);
            grid.add(labelA1, 1, 4);
            grid.add(labelA2, 1, 5);
            grid.add(labelA3, 1, 6);
            grid.add(labelExam, 1, 7);

            grid.add(tfStudentName, 2, 2);
            grid.add(tfQuiz, 2, 3);
            grid.add(tfA1, 2, 4);
            grid.add(tfA2, 2, 5);
            grid.add(tfA3, 2, 6);
            grid.add(tfExam, 2, 7);

            grid.add(studentIdErrorLabel,3,1);
            grid.add(studentNameErrorLabel,3,2);
            grid.add(quizErrorLabel,3,3);
            grid.add(a1ErrorLabel,3,4);
            grid.add(a2ErrorLabel,3,5);
            grid.add(a3ErrorLabel,3,6);
            grid.add(examErrorLabel,3,7);


            dialog.setHeaderText(header);
            dialog.setTitle(title);

            dialog.getDialogPane().setContent(grid);

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();
            String[] stringArray = {tfStudentId.getText(),tfStudentName.getText(),tfQuiz.getText(),tfA1.getText(),
                    tfA2.getText(),tfA3.getText(),tfExam.getText()};
            ArrayList<Integer> errorList= gradeControl.checkInputError(stringArray);
            String[] dataInput = {" ID","Name","Quiz", "Assignment 1", "Assignment 2", "Assignment 3", "Exam"};
            if (result.get() == ButtonType.OK) {
                if ((errorList.size()!=0)){
                    System.out.println("There is error processing data");
                    String errorString = "";
                    for(int i = 0; i < errorList.size(); i++) {
                        errorString += dataInput[errorList.get(i)] + ", ";
                    }
                    alert.setHeaderText("Error ");
                    alert.setContentText("There is an error in " + errorString + "  Please try again.");
                    alert.showAndWait();
                }
                else {
                    StudentRecord studentRecord = gradeControl.createStudentRecord(Integer.parseInt(stringArray[0]),
                            stringArray[1],
                            Integer.parseInt(stringArray[2]),
                            Integer.parseInt(stringArray[3]),
                            Integer.parseInt(stringArray[4]),
                            Integer.parseInt(stringArray[5]),
                            Integer.parseInt(stringArray[6]));

                    if(functionState==FunctionState.INSERT){
                        if(!(gradeControl.addStudentIntoDatabase(studentRecord))) {
                            alert.setTitle("Error!!");
                            alert.setContentText("Student with same ID already exist.");
                            alert.showAndWait();
                        }
                    }
                    else if(functionState==FunctionState.UPDATE){
                        gradeControl.updateDatabase(studentRecord);
                    }
                }
            }
        }
    }


    /*
    search() function take the user input by the help of comboBox and textField to determine what item in
    the database need to be searched. At the end of the process the data is updated.
     */
    private void search(){
        Dialog<ButtonType> searchDialog = new Dialog<>();
        searchDialog.setTitle("Search Database.");
        searchDialog.setHeaderText("Press OK to SEARCH into Database.");

        String[] searchArray = {"ID","Name","Quiz", "Assignment 1", "Assignment 2", "Assignment 3",
                "Exam","Result","Grade"};
        ComboBox <String> searchBox = new ComboBox<>();
        ObservableList<String> searchItem = FXCollections.observableArrayList(searchArray);
        searchBox.getItems().addAll(searchItem);

        //Setting value of searchBox and updating it as necessary.
        searchBox.setValue("ID");
        EventHandler<ActionEvent> eventSearch = search -> searchBox.setValue(searchBox.getValue());
        searchBox.setOnAction(eventSearch);

        Label labelEnter = new Label("Enter: ");
        Label labelSearchItem = new Label("Please choose option");
        TextField enterText = new TextField();

        GridPane grid = new GridPane();
        grid.add(labelSearchItem, 1, 1);
        grid.add(searchBox,2,1);
        grid.add(labelEnter,1,2);
        grid.add(enterText, 2, 2);
        searchDialog.getDialogPane().setContent(grid);
        searchDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        Optional<ButtonType> result = searchDialog.showAndWait();
        if(result.get()==ButtonType.OK){
            if(gradeControl.errorSearch(searchBox.getValue(),enterText.getText())){
                gradeControl.searchDatabase(searchBox.getValue(),enterText.getText());
                mainLabel.setText("Only showing search result, press any button to view all data.");

            }
            else{
                alert.setHeaderText("Data type mismatch.");
                alert.setContentText(String.format("Error in %s format, Please check ",searchBox.getValue()));
                alert.showAndWait();
            }
        }
    }


    /*
    calculate() method is  show a dialog box that user can input with all the information to calculate
    the result or grade and once calculated the result is showed as alert.
     */
    private void calculate(){
        Dialog<ButtonType> calculateDialog = new Dialog<>();
        calculateDialog.setTitle("Calculate." );
        calculateDialog.setHeaderText("Press OK to Calculate.");

        GridPane grid = new GridPane();
        grid.setMinWidth(450);
        grid.add(labelQuiz, 1, 3);
        grid.add(labelA1, 1, 4);
        grid.add(labelA2, 1, 5);
        grid.add(labelA3, 1, 6);
        grid.add(labelExam, 1, 7);

        grid.add(tfQuiz, 2, 3);
        grid.add(tfA1, 2, 4);
        grid.add(tfA2, 2, 5);
        grid.add(tfA3, 2, 6);
        grid.add(tfExam, 2, 7);

        grid.add(quizErrorLabel,3,3);
        grid.add(a1ErrorLabel,3,4);
        grid.add(a2ErrorLabel,3,5);
        grid.add(a3ErrorLabel,3,6);
        grid.add(examErrorLabel,3,7);

        calculateDialog.getDialogPane().setContent(grid);
        calculateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = calculateDialog.showAndWait();
        String[] stringArray = {tfQuiz.getText(),tfA1.getText(),tfA2.getText(),tfA3.getText(),tfExam.getText()};
        String[] dataInput = {"Quiz", "Assignment 1", "Assignment 2", "Assignment 3", "Exam"};
        String error = "";
        for(int i=0;i < stringArray.length; i++){
            if(!(gradeControl.inputMarksCheck(stringArray[i]))){
                error += dataInput[i] + ", ";
            }
        }
        if (result.get() == ButtonType.OK) {
            if ((error.length()>0)){
                alert.setHeaderText("Input Format Mismatch.");
                alert.setContentText("There is an error in " + error + "  Please try again.");
                alert.showAndWait();

            }
            else {
                String calculatedResult = gradeControl.calculateResults(Integer.parseInt(stringArray[0]),
                        Integer.parseInt(stringArray[1]),
                        Integer.parseInt(stringArray[2]),
                        Integer.parseInt(stringArray[3]),
                        Integer.parseInt(stringArray[4]));
                String contentToDisplay = String.format("The result is "+ calculatedResult);
                if(functionState == FunctionState.CALCULATE_GRADE) {
                    contentToDisplay = "The Grade is:" + gradeControl.calculateGrade(Double.parseDouble(calculatedResult));
                }
                info.setHeaderText(null);
                info.setContentText(contentToDisplay);
                info.showAndWait();
            }
        }
    }

    /*
    populateTable create a table clear the data stored in grade control that is responsible for table view and a data is
    fetched from the database and the value is assigned to the data by populateTableWithAllData method.
     */
    private void populateTable(){
        if(!(functionState == FunctionState.SEARCH ||
                functionState == FunctionState.CALCULATE_RESULT ||
                functionState == FunctionState.CALCULATE_GRADE)){
            gradeControl.data.clear();
            gradeControl.populateTableWithAllData();
        }
    }


}