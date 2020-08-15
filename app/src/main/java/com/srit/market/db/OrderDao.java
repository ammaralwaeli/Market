package com.srit.market.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orderitem")
    List<OrderItem> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderItem... orderItem);

    @Delete
    void delete(OrderItem... user);

}
