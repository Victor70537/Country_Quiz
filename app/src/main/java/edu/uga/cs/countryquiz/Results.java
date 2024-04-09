package edu.uga.cs.countryquiz;

public class Results {

    private int id;

    private String date;
    private int grade;


    public Results ()
    {
        this.id = -1;
        this.date = null;
        this.grade = -1;

    }

    public Results ( String date, int grade ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.date = date;
        this.grade = grade;
    }

    public long getID() {return id; }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getGrade()
    {
        return grade;
    }

    public String getDate() { return date; }

    public String toString()
    {
        return id + ": " + " " + date + " " + grade + "/6";
    }

}
