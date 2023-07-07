package com.example.allergendetector;

public class User {
    private  String fullName,userName, email,
            phoneNumber,birthDate, passWord;
    //this default constructor is required for Firebase
    public User(){

    }
    public User(String fullName,String userName,
                String email, String phoneNumber,String birthDate, String passWord) {
        this.fullName = fullName;
        this.userName = userName;
        this.birthDate= birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getBirthDate(){
        return birthDate;
    }
    public void setBirthDate() {
        this.birthDate = birthDate;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(){
        this.phoneNumber= phoneNumber;
    }
    public String getPassWord(){
        return passWord;
    }
    public void setPassword(){
        this.passWord = passWord;
    }

}
