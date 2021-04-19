package com.example.homeworkday08.model;

public class Contact {
    private String name, phone, imgUri;
    private int imgDefault;
    private boolean isImage;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact(String name, String phone, String imgUri) {
        this.name = name;
        this.phone = phone;
        this.imgUri = imgUri;
    }

    public Contact(String name, String phone, int imgDefault) {
        this.name = name;
        this.phone = phone;
        this.imgDefault = imgDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public int getImgDefault() {
        return imgDefault;
    }

    public void setImgDefault(int imgDefault) {
        this.imgDefault = imgDefault;
    }

    public void setIsImage(boolean isImage){
        this.isImage = isImage;
    }

    public boolean isImage(){
        return isImage;
    }
}
