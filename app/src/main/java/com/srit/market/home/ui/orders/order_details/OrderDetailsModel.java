package com.srit.market.home.ui.orders.order_details;

import java.io.Serializable;

public class OrderDetailsModel implements Serializable {
    private int itemCounts;
    private int price;
    private String itemName;
    private int discount;
    private String photo;

    public int getItemCounts() {
        return itemCounts;
    }

    public String getItemCountsAsString() {
        return itemCounts + "";
    }

    public int getPrice() {
        return price;
    }

    public String getPriceAsString() {
        return price + "";
    }

    public String getItemName() {
        return itemName;
    }

    public int getDiscount() {
        return discount;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return
                "OrderDetailsModel{" +
                        "itemCounts = '" + itemCounts + '\'' +
                        ",price = '" + price + '\'' +
                        ",itemName = '" + itemName + '\'' +
                        ",discount = '" + discount + '\'' +
                        ",photo = '" + photo + '\'' +
                        "}";
    }
}