package com.example.comp_4200project;


public class MyData {

    public String butText;

    public int studentID;
    public String studentName;
    public String firstName;
    public String lastName;
    public String birthdate;
    public String faculty;
    public double height;
    public double weight;
    public String team;
    public int jerseyNumber;

    public MyData(int StudentID, String studentName, String firstName, String lastName, String birthdate,
                  String faculty, double height, double weight, String team, int jerseyNumber){

            this.butText = butText;
            this.studentID = StudentID;
            this.studentName = studentName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.faculty = faculty;
            this.height = height;
            this.weight = weight;
            this.team = team;
            this.jerseyNumber = jerseyNumber;
        }


    public String getButText(){ return butText;}

    public void Athlete(int studentID, String studentName, String firstName, String lastName, String birthdate,
                        String faculty, double height, double weight, String team, int jerseyNumber) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.faculty = faculty;
        this.height = height;
        this.weight = weight;
        this.team = team;
        this.jerseyNumber = jerseyNumber;
    }

    // Getter methods
    public int getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getFaculty() {
        return faculty;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getTeam() {
        return team;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

}