package edu.uga.cs.countryquiz;

public class Results {

    private int id;

    private int grade;


    public Results ()
    {
        this.id = -1;
        this.grade = -1;

    }

    public Results ( int grade ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.grade = grade;
    }

    public long getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getGrade()
    {
        return grade;
    }

    public String toString()
    {
        return id + ": " + grade + "/6";
    }

}
