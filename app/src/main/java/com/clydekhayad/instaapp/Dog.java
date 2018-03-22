package com.clydekhayad.instaapp;

/**
 * Created by Clyde A. Khayad on 3/10/2018.
 */

public class Dog {

    private String name, breed, image, sex, birthdate, transfer, color, user_id;

    public Dog() {

    }

    public Dog(String name, String breed, String image, String sex, String birthdate, String transfer, String color, String user_id) {
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.sex = sex;
        this.birthdate = birthdate;
        this.transfer = transfer;
        this.color = color;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getImage() {
        return image;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getTransfer() {
        return transfer;
    }

    public String getColor() {
        return color;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setName(String dog_name) {
        this.name = dog_name;
    }

    public void setBreed(String dog_breed) {
        this.breed = dog_breed;
    }

    public void setImage(String dog_photo) {
        this.image = dog_photo;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
