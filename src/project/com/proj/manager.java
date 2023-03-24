package com.proj;

public class manager extends person {

    String FName;
    String MName;
    String LName;
    int shopId;

    public manager() {
    }

    public manager(int id, String email, String password, String FName, String MName, String LName, int shopId) {
        super(id, email, password);
        this.FName = FName;
        this.MName = MName;
        this.LName = LName;
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "manager{" +
                "FName='" + FName + '\'' +
                ", MName='" + MName + '\'' +
                ", LName='" + LName + '\'' +
                ", shopId=" + shopId +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
