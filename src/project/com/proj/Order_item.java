package com.proj;

import javax.swing.*;
import java.awt.*;

public class Order_item extends Product {
    int id ;
    int Quantity;
    float totalP;
    int order_id;

    public Order_item (){}


    public Order_item (int pid , String name, int q , float p, Image img){
        super.pid = pid ;
        super.Name = name;
        this.Quantity = q ;
        super.price = p;
        super.img = img;

    }

    public Order_item (int pid , String name, int q , float p){
        super.pid = pid ;
        super.Name = name;
        this.Quantity = q ;
        super.price = p;
    }

    public Order_item (int pid ,String name, int q , float p  , int oid, Image img){
        super.pid = pid ;
        super.Name = name;
        super.img = img;
        this.Quantity = q ;
        this.totalP = p ;
        this.order_id = oid;


    }

    public Order_item (int pid ,String name, int q , float p  , int oid, Icon img){
        super.pid = pid ;
        super.Name = name;
        super.img = iconToImage(img);
        this.Quantity = q ;
        this.totalP = p ;
        this.order_id = oid;

    }

    @Override
    public String toString() {
        return "Order_item{" +
                "id=" + id +
                ", Quantity=" + Quantity +
                ", totalP=" + totalP +
                ", order_id=" + order_id +
                ", pid=" + pid +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
