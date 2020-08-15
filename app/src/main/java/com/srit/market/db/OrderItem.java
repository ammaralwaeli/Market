package com.srit.market.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OrderItem {

    @PrimaryKey
    public int item_id;

    @ColumnInfo(name = "item_name")
    public String itemName;

    @ColumnInfo(name = "photo")
    public String photo;

    @ColumnInfo(name = "item_price")
    public int itemPrice;

    @ColumnInfo(name = "item_count")
    public int itemCount;

    @ColumnInfo(name = "total_price")
    public int totalPrice;

    public String getItemCountAsString() {
        return itemCount+"";
    }

    public String getTotalPriceAsString() {
        return totalPrice+"";
    }

    public OrderItem(int item_id, String itemName, String photo, int itemPrice, int itemCount, int totalPrice) {
        this.item_id = item_id;
        this.itemName = itemName;
        this.photo = photo;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "item_id=" + item_id +
                ", itemName='" + itemName + '\'' +
                ", photo='" + photo + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemCount=" + itemCount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
