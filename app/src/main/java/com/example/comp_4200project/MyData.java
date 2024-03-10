package com.example.comp_4200project;


public class MyData {
    public String text;
    public String butText;
    public int image;

    public int buttonColor;
    public MyData(String text, int image, String butText){
        this.text = text;
        this.image = image;
        this.butText = butText;

    }
    public int getImage(){
        return  image;
    }
    public String getText(){
        return text;
    }
    public String getButText(){ return butText;}


}