package com.proj;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Product {
    int pid;
    String Name;
    String Description;
    float price;
    int category;
    Image img;

    Product(int id, String name, String Description, float price, Image img ){
        this.pid =id;
        this.Name =name;
        this.Description = Description;
        this.price = price;
        this.img = img;
    }

    Product(int id, String name, String Description, float price, Icon img ){
        this.pid =id;
        this.Name =name;
        this.Description = Description;
        this.price = price;
        this.img = iconToImage(img);
    }

    Product(int id, String name , Icon img ){
        this.pid =id;
        this.Name = name;
        this.img = iconToImage(img);
    }

    Product(int id, String name ,Image img ){
        this.pid =id;
        this.Name = name;
        this.img = img;

    }

    public Product() {

    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", img=" + img +
                '}';
    }

    Image iconToImage(Icon icon) {
        if(icon instanceof ImageIcon)
        {
            return ((ImageIcon) icon).getImage();
        }
        else
        {
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            icon.paintIcon(null, image.getGraphics(), 0, 0);
            return image;
        }
    }

    }

