package com.srit.market.home.ui.orders.order_details;

import java.util.List;

public class AllOrders {
    private int totalPrice;
    private List<OrderDetailsModel> results;

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetailsModel> getResults() {
        return results;
    }
}
