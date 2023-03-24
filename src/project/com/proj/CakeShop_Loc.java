package com.proj;

public class CakeShop_Loc extends CakeShop{
    String Location;

    public CakeShop_Loc(){}

    public CakeShop_Loc(String Location){
        this.Location = Location;
    }

    public CakeShop_Loc(int id , String city , String contact, String Location){
        super(id,city,contact);
        this.Location = Location;
    }

    }
