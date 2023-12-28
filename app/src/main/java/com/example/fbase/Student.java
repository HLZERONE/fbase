package com.example.fbase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {

    static final String db = "students", dbName = "name", dbCourse = "course", dbEmail = "email", dbImg = "imgUrl";
    String name, course, email, imgUrl, id;

    public Student(){

    }
    public Student(String name, String course, String email, String imgUrl) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
