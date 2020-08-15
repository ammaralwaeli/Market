package com.srit.market.home.ui.orders.order_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.srit.market.R;
import com.srit.market.databinding.ItemOrderBinding;
import com.srit.market.databinding.ItemOrderDetailsBinding;
import com.srit.market.home.ui.orders.OrderModel;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private List<OrderDetailsModel> modelList;
    private Context context;


    public OrderDetailsAdapter(Context context, List<OrderDetailsModel> list) {
        this.context = context;
        this.modelList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderDetailsBinding binding;

        ViewHolder(@NonNull ItemOrderDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderDetailsModel dayModel) {
            binding.setItem(dayModel);
            binding.hasPendingBindings();
        }
    }


    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemOrderDetailsBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_order_details, viewGroup, false);
        return new OrderDetailsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, final int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
