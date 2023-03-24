package com.proj;

public class customer extends person {

    String phone_Num;
    String name;
    String address;

    public customer(String phone_Num) {
        this.phone_Num = phone_Num;
    }

    public customer(int id, String email, String password, String phone_Num, String name) {
        super(id, email, password);
        this.phone_Num = phone_Num;
        this.name = name;
    }

    public customer() {

    }

    @Override
    public String toString() {
        return "customer{" +
                "phone_Num='" + phone_Num + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
