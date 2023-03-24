package com.proj;
import java.util.Date;

public class payment {
    int id;
    Date date;
    float amount;
    int Mng_id;

    public payment (){}

    public payment (int id, Date d, float amount , int mng_id){
        this.id = id ;
        this.date = d;
        this.amount = amount ;
        this.Mng_id = mng_id;
    }

}
