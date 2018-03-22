package com.clydekhayad.instaapp;

/**
 * Created by Clyde A. Khayad on 3/3/2018.
 */

public class Insta {

    private String title, desc, image;

    public Insta() {

    }

    public Insta(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
