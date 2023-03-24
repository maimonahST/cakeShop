package com.proj;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Rating extends Product{
    Date date_rec;
    int score;
    String feedback;


    public Rating (int pid , String Pname, int sc , String f , Image img){
        super.pid = pid;
        super.Name = Pname;
        this.score = sc;
        this.feedback = f;
        super.img = img;
    }

    public Rating (String feedback , Date date){
        this.feedback = feedback;
        this.date_rec = date;
    }

    public Rating (int pid , String Pname, int sc , String f , Icon img){
        super.pid = pid;
        super.Name = Pname;
        this.score = sc;
        this.feedback = f;
        super.img = iconToImage(img);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "pid=" + pid +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", date_rec=" + date_rec +
                ", score=" + score +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
