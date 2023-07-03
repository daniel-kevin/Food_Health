package com.example.foodhealth;

public class User {

    String fullname, date, phone, email, password;

    User(){}

//    public User (String fullname, String date, String phone, String email, String password) {
//        this.fullname = fullname;
//        this.date = date;
//        this.phone = phone;
//        this.email = email;
//        this.password = password;
//    }

    public String getPhone() {
        return phone;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }
    public String getDate() {
        return date;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
