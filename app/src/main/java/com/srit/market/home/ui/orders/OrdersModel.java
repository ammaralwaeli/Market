package com.srit.market.home.ui.orders;

import java.util.List;

public class OrdersModel {
    List<OrderModel> orders;

    public List<OrderModel> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "OrdersModel{" +
                "orders=" + orders +
                '}';
    }
}
