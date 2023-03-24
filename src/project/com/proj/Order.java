package com.proj;

import java.time.LocalDate;
import java.util.Date;

public class Order {

    int order_id;
    String order_states;
    Date order_date;
    int Mng_id ;
    int cust_id;
    int payment_id;
    int shop_id ;
    String shopLoc;

    public Order(){

    }

    public Order(int order_id, String order_states, Date order_date, int mng_id, int cust_id, int payment_id, int shop_id, String shopLoc) {
        this.order_id = order_id;
        this.order_states = order_states;
        this.order_date = order_date;
        Mng_id = mng_id;
        this.cust_id = cust_id;
        this.payment_id = payment_id;
        this.shop_id = shop_id;
        this.shopLoc = shopLoc;
    }

    public Order(int order_id, String order_states, Date order_date) {
        this.order_id = order_id;
        this.order_states = order_states;
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_states='" + order_states + '\'' +
                ", order_date=" + order_date +
                ", Mng_id=" + Mng_id +
                ", cust_id=" + cust_id +
                ", payment_id=" + payment_id +
                ", shop_id=" + shop_id +
                ", shopLoc='" + shopLoc + '\'' +
                '}';
    }
}
