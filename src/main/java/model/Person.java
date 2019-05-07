package model;

import java.time.LocalDate;

public class Person {

    private int id;
    private String fname;
    private String lname;
    private Color eyesColor;
    private LocalDate birthdayDate;

    public Person() {
    }

    public Person(int id, String fname, String lname, Color eyesColor, LocalDate birthdayDate) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.eyesColor = eyesColor;
        this.birthdayDate = birthdayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Color getEyesColor() {
        return eyesColor;
    }

    public void setEyesColor(Color eyesColor) {
        this.eyesColor = eyesColor;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
}
