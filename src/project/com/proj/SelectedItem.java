package com.proj;

public class SelectedItem {
    int  itemID;
    int quantity;

    SelectedItem(int itemID, int quantity){
        this.itemID =itemID;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SelectedItem{" +
                "itemID='" + itemID + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}


