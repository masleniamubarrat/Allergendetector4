package com.example.allergendetector;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class User {
    private  String birthDate, email, fullName, passWord, phoneNumber,userName, review;
    int rating, like;
    private String profilePictureUrl;
    private List<String> itemList;

    //this default constructor is required for Firebase
    public User(){
        itemList = new ArrayList<>();

    }
    public User(String fullName, String userName,
                String email, String phoneNumber, String birthDate, String passWord, String review, int rating,String profilePictureUrl, List itemList,int like) {
        this.fullName = fullName;
        this.userName = userName;
        this.birthDate= birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
        this.rating = 0;
        this.review = "";
        this.profilePictureUrl="";
        this.itemList = new ArrayList<>();
        this.like = 0;

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
    public int getRating(){
        return rating;
    }
    public void setRating(int rating){
        this.rating = rating;
    }


    public String getReview(){
        return review;
    }
    public void setReview(){
        this.review = review;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public List<String> getItemList() {
        return itemList;
    }

    public void addItemToList(String item) {
        itemList.add(item);
    }

    public int getLike(){
        return like;
    }
    public void setLike(){
        this.like = like;
    }




}
