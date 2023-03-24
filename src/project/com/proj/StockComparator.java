package com.proj;

import java.util.Comparator;

class StockComparator implements Comparator<SelectedItem> {

    // override the compare() method
    public int compare(SelectedItem s1, SelectedItem s2)
    {
        if (s1.itemID == s2.itemID)
            return 0;
        else if (s1.itemID > s2.itemID)
            return 1;
        else
            return -1;
    }
}
