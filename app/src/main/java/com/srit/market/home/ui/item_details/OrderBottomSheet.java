package com.srit.market.home.ui.item_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.srit.market.databinding.BottomSheetAddOrderBinding;
import com.srit.market.db.OrderItem;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.home.ui.home.item.ItemModel;

public class OrderBottomSheet extends BottomSheetDialogFragment {
    BottomSheetAddOrderBinding binding;
    private int numOfItems = 1;
    private int totalPrice = 0;
    static ItemModel itemModel;

    public static OrderBottomSheet newInstance(ItemModel item) {
        itemModel = item;
        return new OrderBottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAddOrderBinding.inflate(inflater, container, false);
        totalPrice = recalculatePrice();
        binding.header.setText("Total Price : " + totalPrice);

        setListener();
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
                dismiss();
            }
        });
        return binding.getRoot();
    }

    private void order(){
        OrderItemRepository order=new OrderItemRepository(getContext());
        OrderItem orderItem=new OrderItem(itemModel.getId(),
                itemModel.getItemName(),
                itemModel.getPhoto(),
                Integer.parseInt(itemModel.getPrice()),
                numOfItems,totalPrice);
        order.insertItem(orderItem);
    }

    private int recalculatePrice(){
        int price=Integer.parseInt(itemModel.getPrice());
        int discount=(numOfItems*Integer.parseInt(itemModel.getDiscount()));
        return (numOfItems*price) - discount ;
    }

    private void setListener() {
        binding.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numOfItems == 1) {
                    return;
                }
                numOfItems--;
                totalPrice=recalculatePrice();
                binding.header.setText("Total Price : " + totalPrice);
                binding.numOfItems.setText(numOfItems + "");
            }
        });

        binding.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfItems++;
                totalPrice=recalculatePrice();
                binding.header.setText("Total Price : " + totalPrice);
                binding.numOfItems.setText(numOfItems + "");
            }
        });
    }
}
