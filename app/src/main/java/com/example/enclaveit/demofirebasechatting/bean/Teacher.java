package com.example.enclaveit.demofirebasechatting.bean;

/**
 * Created by enclaveit on 13/04/2017.
 */

public class Teacher {
    private int teacherID;
    private String teacherName;
    private String teacherEmail;
    private String teacherPhone;
    private String teacherAddress;
    private String teacherBirthDate;

    public Teacher(){
    }

    public Teacher(int teacherID, String teacherName, String teacherEmail, String teacherPhone, String teacherAddress, String teacherBirthDate) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherPhone = teacherPhone;
        this.teacherAddress = teacherAddress;
        this.teacherBirthDate = teacherBirthDate;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public String getTeacherAddress() {
        return teacherAddress;
    }

    public String getTeacherBirthDate() {
        return teacherBirthDate;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    public void setTeacherBirthDate(String teacherBirthDate) {
        this.teacherBirthDate = teacherBirthDate;
    }
}
