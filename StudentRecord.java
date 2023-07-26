/*
Created by Bikram Shrestha
This class is used as a model class for storing all the information
regarding student's record. This model class provide method to get
and set the value of the student which includes id, student name,
marks obtained in quiz, assessment 1,2 and 3 as well as from exam,
as well as result and grade.
Constructor for creating StudentRecord by passing all the required
data is included to create StudentRecord object.
There is no setter for data is provided.
*/

public class StudentRecord{

	private int idNumber;       // Store student identity number.
	private String studentName; // Store student name.
	private int quiz;           // Store student quiz mark.
	private int a1;             // Store student assignment1 mark.
	private int a2;             // Store student assignment2 mark.
	private int a3;             // Store student assignment3 mark.
	private int exam;           // Store student exam mark.
	private String result;      // Store student result.
	private String grade;       // Store student grade.

    //Overloaded constructor for creating StudentRecord Object.
	public StudentRecord ( int idNumber,String studentName,
		int quiz, int a1, int a2, int a3,
		int exam, String result, String grade){
		this.idNumber = idNumber;
		this.studentName = studentName;
		this.quiz = quiz;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.exam = exam;
		this.result = result;
		this.grade = grade;
	}

    // Getter for idNumber
	public int getIdNumber(){
		return idNumber;
	}

    // Getter for studentName
    public String getStudentName(){
		return studentName;
	}

    // Getter for quiz
    public int getQuiz(){
		return quiz;
	}

    // Getter for a1
	public int getA1(){
		return a1;
	}

    // Getter for a2
	public int getA2(){
		return a2;
	}

    // Getter for a3
	public int getA3(){
		return a3;
	}

    // Getter for exam
	public int getExam(){
		return exam;
	}

    // Getter for result
	public String getResult(){
		return result;
	}

    // Getter for grade
	public String getGrade(){
		return grade;
	}

	//Override toString method is created for testing.
	public String toString(){
		return studentName + "'s result is " + result +
                " Grade is " + grade;
	}
}