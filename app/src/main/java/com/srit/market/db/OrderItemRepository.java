package com.srit.market.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

class DeleteShoppingCartItemsAsyncTask extends AsyncTask<OrderItem, Void, Void> {

    OrderDao dao;

    public DeleteShoppingCartItemsAsyncTask(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(OrderItem... orderItems) {
        dao.delete(orderItems);
        return null;
    }
}


class InsertShoppingCartItemsAsyncTask extends AsyncTask<OrderItem, Void, Void> {

    OrderDao dao;

    public InsertShoppingCartItemsAsyncTask(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(OrderItem... orderItems) {
        dao.insert(orderItems);
        return null;
    }
}


class SelectShoppingItemsAsyncTask extends AsyncTask<Void,Void, List<OrderItem>> {

    OrderDao dao;

    public SelectShoppingItemsAsyncTask(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    protected List<OrderItem> doInBackground(Void... voids) {
        return dao.getAll();
    }
}



public class OrderItemRepository {
    private OrderDao dao;
    public OrderItemRepository(Context context) {
        dao=AppDatabase.getDatabase(context).orderDao();
    }


    public void deleteItem(OrderItem... orderItems) {
        new DeleteShoppingCartItemsAsyncTask(dao).execute(orderItems);
    }

    public void insertItem(OrderItem... orderItems) {
        boolean updated = false;

        for (OrderItem item : getItems()) {
            if (item.item_id == orderItems[0].item_id) {
                OrderItem newItem = new OrderItem(item.item_id,
                        item.itemName,
                        item.photo,
                        item.itemPrice,
                        orderItems[0].itemCount + item.itemCount,
                        item.totalPrice + orderItems[0].totalPrice);
                new InsertShoppingCartItemsAsyncTask(dao).execute(orderItems);
                updated = true;
                break;
            }
        }
        if (!updated) {
            new InsertShoppingCartItemsAsyncTask(dao).execute(orderItems);
        }
    }

    public List<OrderItem> getItems() {
        try {
            return new SelectShoppingItemsAsyncTask(dao).execute().get();
        }
        catch (Exception e){
            return null;
        }
    }

}
